/*
 * Copyright 2025 marlonlom
 * SPDX-License-Identifier: Apache-2.0
 */
package com.github.marlonlom.colombianholidayscalculator.domain;

import com.github.marlonlom.colombianholidayscalculator.util.LocalDateUtil;

import java.util.Date;

/**
 * Defines a contract for objects capable of finding a specific holiday date
 * within a given year.
 *
 * @author marlonlom
 * @version 2.0.0
 */
public interface HolidayDateFindable {

  /**
   * Finds the specific date of a holiday for a given year.
   *
   * @param dateUtil An instance of {@link LocalDateUtil} to assist with date
   *                 calculations.
   * @param year     The calendar year for which to find the holiday date.
   * @return A {@link Date} object representing the found holiday date, or
   * {@code null} if the date cannot be determined.
   */
  Date finDate(LocalDateUtil dateUtil, int year);
}
