/*
 * Copyright 2025 marlonlom
 * SPDX-License-Identifier: Apache-2.0
 */
package com.github.marlonlom.colombianholidayscalculator;

import com.github.marlonlom.colombianholidayscalculator.config.ConfigProperties;
import com.github.marlonlom.colombianholidayscalculator.domain.HolidayDates;
import com.github.marlonlom.colombianholidayscalculator.util.LocalDateUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class for calculating Colombia Holidays.
 *
 * @author marlonlom
 * @version 2.0.0
 * @see http://bit.ly/18bKP7r
 */
public class HolidaysCalculator {

  /**
   * Utility class for handling {@link java.time.LocalDate} objects.
   */
  private final LocalDateUtil localDateUtil;

  /**
   * Configuration properties for the application.
   */
  private final ConfigProperties configProperties;

  /**
   * Constructs a new `HolidaysCalculator` instance.
   */
  public HolidaysCalculator() {
    super();
    this.localDateUtil = new LocalDateUtil();
    this.configProperties = new ConfigProperties();
  }

  /**
   * Constructs a `HolidaysCalculator` instance, with the specified date utility
   * and configuration properties.
   *
   * @param localDateUtil    An instance of `LocalDateUtil` for date-related
   *                         operations.
   * @param configProperties An instance of `ConfigProperties` containing
   *                         application configuration.
   */
  protected HolidaysCalculator(LocalDateUtil localDateUtil, ConfigProperties configProperties) {
    super();
    this.localDateUtil = localDateUtil;
    this.configProperties = configProperties;
  }

  /**
   * Returns the configuration properties for the application.
   *
   * @return the configuration properties instance.
   */
  public ConfigProperties getConfigProperties() {
    return configProperties;
  }

  /**
   * Retrieves the formatted holiday date for a given position and year.
   *
   * @param pos  The ordinal position of the desired holiday.
   * @param year The year for which to retrieve the holiday date.
   * @return The formatted holiday date string, or an empty string if the holiday
   * is not found.
   */
  private String getHolidayDate(int pos, Integer year) {
    Optional<HolidayDates> optionalHolidayDate = HolidayDates.indexOf(pos);
    if (!optionalHolidayDate.isPresent()) {
      throw new IllegalArgumentException("Could not find a holiday date.");
    }
    final Date foundDate = optionalHolidayDate.get().getFindable().finDate(getLocalDateUtil(), year);
    return getConfigProperties().getHolidayDateFormat().format(foundDate);
  }

  /**
   * Retrieves a {@code HolidaysCalculatorResponse} object containing holidays for
   * a given year.
   *
   * @param year The year for which to retrieve holidays.
   * @return A {@code HolidaysCalculatorResponse} where keys represent holiday
   * dates or names, and values are holiday details.
   */
  public final HolidaysCalculatorResponse getHolidays(Integer year) {
    try {
      System.out.println("getHolidays / starting");
      final HolidaysCalculatorResponse response = new HolidaysCalculatorResponse(
        getConfigProperties().getHolidayDateFormat().toPattern());
      if (!getConfigProperties().isReady()) {
        throw new IllegalArgumentException("Config properties not initialized.");
      }
      final List<String> holidayDetails = this.getConfigProperties().getHolidayDetails();
      IntStream.range(0, holidayDetails.size())
        .mapToObj(pos -> String.format("%s;%s", this.getHolidayDate(pos, year), holidayDetails.get(pos)))
        .collect(Collectors.toList()).forEach(text -> {
          String[] parts = text.split(";");
          response.getHolidays().put(parts[0], parts[1]);
        });
      return response;
    } catch (RuntimeException exception) {
      System.err.println("getHolidays / failed");
      return new HolidaysCalculatorResponse(exception);
    } finally {
      System.out.println("getHolidays / finished");
    }
  }

  /**
   * Returns the utility class for handling {@link java.time.LocalDate} objects.
   *
   * @return the local date utility instance.
   */
  public LocalDateUtil getLocalDateUtil() {
    return localDateUtil;
  }
}
