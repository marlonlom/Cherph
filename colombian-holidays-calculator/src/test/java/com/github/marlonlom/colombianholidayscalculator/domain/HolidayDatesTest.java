package com.github.marlonlom.colombianholidayscalculator.domain;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

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
