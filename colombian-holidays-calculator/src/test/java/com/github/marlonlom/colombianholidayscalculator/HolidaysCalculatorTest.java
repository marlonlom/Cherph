package com.github.marlonlom.colombianholidayscalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Test;

public class HolidaysCalculatorTest {

	private HolidaysCalculator holidaysCalculator = new HolidaysCalculator();

	@Test
	public void shouldShowColombianHolidaysFor2024() {
		JSONObject holidays = holidaysCalculator.getHolidays(2024);
		System.out.println(String.format("holidays =%s", holidays.toString()));
		assertNotNull(holidays);
		assertFalse(holidays.isEmpty());
		assertEquals(20, holidays.length());
	}
}
