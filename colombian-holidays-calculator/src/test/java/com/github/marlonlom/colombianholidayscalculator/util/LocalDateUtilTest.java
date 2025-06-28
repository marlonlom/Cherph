/*
 * Copyright 2025 marlonlom
 * SPDX-License-Identifier: Apache-2.0
 */
package com.github.marlonlom.colombianholidayscalculator.util;

import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class LocalDateUtilTest {

  private LocalDateUtil localDateUtil = new LocalDateUtil();

  @Test
  public void shouldReturnEasterDayInMarch() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2024);
    LocalDate easterDay = localDateUtil.calculateEasterDay(calendar.get(Calendar.YEAR));
    assertEquals(3, easterDay.getMonthOfYear());
  }

  @Test
  public void shouldReturnEasterDayInApril() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2025);
    LocalDate easterDay = localDateUtil.calculateEasterDay(calendar.get(Calendar.YEAR));
    assertEquals(4, easterDay.getMonthOfYear());
  }

}
