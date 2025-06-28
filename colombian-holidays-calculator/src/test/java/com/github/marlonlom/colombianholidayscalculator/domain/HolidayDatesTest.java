/*
 * Copyright 2025 marlonlom
 * SPDX-License-Identifier: Apache-2.0
 */
package com.github.marlonlom.colombianholidayscalculator.domain;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HolidayDatesTest {

  @Test
  public void shouldNotReturnHolidayDate() {
    Optional<HolidayDates> optional = HolidayDates.indexOf(32);
    Assert.assertFalse(optional.isPresent());
  }

  @Test
  public void shouldReturnValidHolidayDate() {
    Optional<HolidayDates> optional = HolidayDates.indexOf(0);
    Assert.assertTrue(optional.isPresent());
  }

}
