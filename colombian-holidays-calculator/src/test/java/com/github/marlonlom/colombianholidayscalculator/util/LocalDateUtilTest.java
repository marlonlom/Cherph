package com.github.marlonlom.colombianholidayscalculator.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.junit.Test;

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
