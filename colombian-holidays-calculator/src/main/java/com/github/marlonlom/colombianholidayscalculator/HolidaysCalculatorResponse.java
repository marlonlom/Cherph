/*
 * Copyright 2025 marlonlom
 * SPDX-License-Identifier: Apache-2.0
 */
package com.github.marlonlom.colombianholidayscalculator;

import com.github.marlonlom.colombianholidayscalculator.domain.HolidayDateComparator;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * A response object for holiday calculation requests. <br>
 * Contains a map of holidays and any potential failure that occurred during the
 * calculation process. <br>
 * This class is immutable with respect to the holidays map reference, but its
 * contents can be modified.
 *
 * @author marlonlom
 * @version 2.0.0
 */
public final class HolidaysCalculatorResponse implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -3933516128637417621L;

  /**
   * A map containing calculated holiday dates.
   */
  private final Map<String, String> holidays;

  /**
   * Represents an error that occurred during the holiday calculation process.
   */
  private Throwable failure;

  /**
   * Constructs a new {@code HolidaysCalculatorResponse} with a custom date
   * pattern for sorting. <br>
   * Initializes an empty holidays map, sorted chronologically based on the
   * provided date pattern (e.g., {@code "dd/MM/yyyy"}), using
   * {@link HolidayDateComparator}.
   *
   * @param datePattern the date format pattern used to sort holiday keys.
   */
  public HolidaysCalculatorResponse(final String datePattern) {
    super();
    this.holidays = new TreeMap<String, String>(new HolidayDateComparator(datePattern));
  }

  /**
   * Constructs a new {@code HolidaysCalculatorResponse} with a failure cause.
   * <br>
   * Initializes an empty holidays map and sets the failure to the given
   * throwable.
   *
   * @param throwable the cause of failure during holiday calculation
   */
  public HolidaysCalculatorResponse(final Throwable throwable) {
    super();
    this.holidays = new TreeMap<String, String>();
    this.failure = throwable;
  }

  /**
   * Returns the failure (if any) that occurred during the holiday calculation.
   *
   * @return a {@link Throwable} representing the failure, or {@code null} if none
   * occurred
   */
  public Throwable getFailure() {
    return failure;
  }

  /**
   * Returns the map of holidays.
   *
   * @return a map where keys and values represent holiday-related information
   */
  public Map<String, String> getHolidays() {
    return holidays;
  }

  /**
   * Indicates whether the holiday calculation was successful. {@code true} if the
   * operation succeeded; {@code false} otherwise.
   *
   * @return {@code true} if the calculation was successful; {@code false} if a
   * failure occurred
   */
  public boolean isSuccess() {
    return failure == null && !holidays.isEmpty();
  }
}
