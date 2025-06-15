package com.github.marlonlom.colombianholidayscalculator.util;

import java.util.Date;

import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * Utility class for {@link java.time.LocalDate} operations. This class provides
 * helper methods for common tasks involving dates.
 * 
 * @author marlonlom
 * @version 2.0.0
 */
public final class LocalDateUtil {

	/**
	 * Constructs a new {@code LocalDateUtil} instance.
	 */
	public LocalDateUtil() {
		super();
	}

	/**
	 * Calculates the date of Easter Sunday for a given year. This method uses the
	 * Gauss algorithm to determine the date.
	 *
	 * @param year The year for which to calculate Easter Sunday.
	 * @return A {@link LocalDate} representing Easter Sunday for the specified
	 *         year.
	 */
	public LocalDate calculateEasterDay(int year) {

		int a, b, c, d, e;
		int m = 24, n = 5;

		if (year >= 1583 && year <= 1699) {
			m = 22;
			n = 2;
		} else if (year >= 1700 && year <= 1799) {
			m = 23;
			n = 3;
		} else if (year >= 1800 && year <= 1899) {
			m = 23;
			n = 4;
		} else if (year >= 1900 && year <= 2099) {
			m = 24;
			n = 5;
		} else if (year >= 2100 && year <= 2199) {
			m = 24;
			n = 6;
		} else if (year >= 2200 && year <= 2299) {
			m = 25;
			n = 0;
		}

		a = year % 19;
		b = year % 4;
		c = year % 7;
		d = ((a * 19) + m) % 30;
		e = ((2 * b) + (4 * c) + (6 * d) + n) % 7;

		int dia = d + e;

		if (dia < 10) { // March
			return new LocalDate(year, 3, dia + 22);
		} else // April
		{
			if (dia == 26) {
				dia = 19;
			} else if (dia == 25 && d == 28 && e == 6 && a > 10) {
				dia = 18;
			} else {
				dia -= 9;
			}
			return new LocalDate(year, 4, dia);
		}
	}

	/**
	 * Calculates the nearest date to a specified starting date that falls on a
	 * given day of the week. This method delegates to
	 * {@link #getNearestDayOfWeek(LocalDate, int)}.
	 *
	 * @param year        The year of the starting date.
	 * @param monthOfYear The month of the starting date (1-12).
	 * @param dayOfMonth  The day of the month of the starting date.
	 * @param dayOfWeek   The {@link DayOfWeek} (1 for Monday, 7 for Sunday) to find
	 *                    the nearest occurrence of.
	 * @return A {@link LocalDate} representing the nearest date to the specified
	 *         starting date that falls on the given day of the week.
	 */
	public LocalDate getNearestDayOfWeek(int year, int monthOfYear, int dayOfMonth, int dayOfWeek) {
		LocalDate t0 = new LocalDate(year, monthOfYear, dayOfMonth);
		return getNearestDayOfWeek(t0, dayOfWeek);
	}

	/**
	 * Calculates the nearest date to a given starting date that falls on a specific
	 * day of the week. "Nearest" is defined as the day of the week occurring on or
	 * after the `t0` date, and then the closest previous instance if it's closer
	 * than the next instance.
	 *
	 * @param t0        The starting date from which to find the nearest day of the
	 *                  week.
	 * @param dayOfWeek The {@link DayOfWeek} (e.g., {@code DayOfWeek.MONDAY},
	 *                  {@code DayOfWeek.SUNDAY}) to find. This should be an integer
	 *                  value as defined by {@link DayOfWeek#getValue()} (1 for
	 *                  Monday, 7 for Sunday).
	 * @return A {@link LocalDate} representing the nearest date to {@code t0} that
	 *         falls on the specified day of the week.
	 */
	public LocalDate getNearestDayOfWeek(LocalDate t0, int dayOfWeek) {
		LocalDate t1 = t0.withDayOfWeek(dayOfWeek);
		LocalDate t2 = t1.isBefore(t0) ? t1.plusWeeks(1) : t1.minusWeeks(1);
		return Math.abs(Days.daysBetween(t1, t0).getDays()) < Math.abs(Days.daysBetween(t2, t0).getDays()) ? t1 : t2;
	}

	/**
	 * Prepares a {@code Date} object from the given year, month, and day.
	 * <p>
	 * This method utilizes Joda-Time's {@code LocalDate} to construct a date
	 * without time components, then converts it to a standard
	 * {@code java.util.Date}.
	 * </p>
	 *
	 * @param year        The year (e.g., 2023).
	 * @param monthOfYear The month of the year (1-12, where 1 is January).
	 * @param dayOfMonth  The day of the month (1-31).
	 * @return A {@code Date} object representing the specified date with time set
	 *         to midnight (00:00:00).
	 */
	public Date prepareDate(int year, int monthOfYear, int dayOfMonth) {
		LocalDate nd = new LocalDate(year, monthOfYear, dayOfMonth);
		return nd.toDate();
	}
}
