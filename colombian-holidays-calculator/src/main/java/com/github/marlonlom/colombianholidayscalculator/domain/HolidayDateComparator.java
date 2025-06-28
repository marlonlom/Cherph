/*
 * Copyright 2025 marlonlom
 * SPDX-License-Identifier: Apache-2.0
 */
package com.github.marlonlom.colombianholidayscalculator.domain;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Comparator;

/**
 * Comparator for comparing date strings based on a specified date pattern.
 *
 * @author marlonlom
 * @version 2.0.0
 */
public class HolidayDateComparator implements Comparator<String> {

  /**
   * Formatter used to parse date strings according to the specified pattern.
   * Utilizes Joda-Time's {@link DateTimeFormatter}.
   */
  private final DateTimeFormatter dateFormatter;

  /**
   * Constructs a comparator with the given date pattern.
   *
   * @param datePattern the pattern to parse date strings (e.g., "dd/MM/yyyy")
   */
  public HolidayDateComparator(final String datePattern) {
    super();
    this.dateFormatter = DateTimeFormat.forPattern(datePattern);
  }

  @Override
  public int compare(String d1, String d2) {
    LocalDate date1 = dateFormatter.parseLocalDate(d1);
    LocalDate date2 = dateFormatter.parseLocalDate(d2);
    return date1.compareTo(date2);
  }

  /**
   * Returns the {@link DateTimeFormatter} used to parse date strings.
   *
   * @return the configured date formatter
   */
  public DateTimeFormatter getDateFormatter() {
    return dateFormatter;
  }

}
