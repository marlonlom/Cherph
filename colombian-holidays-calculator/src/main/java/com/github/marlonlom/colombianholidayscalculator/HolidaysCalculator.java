package com.github.marlonlom.colombianholidayscalculator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class for calculating Colombia Holidays.
 * 
 * @author marlonlom
 * @see http://bit.ly/18bKP7r
 */
public final class HolidaysCalculator {

	private static final ArrayList<String> holidayDetails = new ArrayList<String>();
	private static final ArrayList<Date> holidayDates = new ArrayList<Date>();

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy");

	/**
	 * Adds a holiday item to the holidays list.
	 * @param prepareDate a Date
	 */
	private static void addHoliday(Date prepareDate) {
		holidayDates.add(prepareDate);
	}

	/**
	 * Returns the easter date for a given year.
	 * @param year
	 * @return a Easter date 
	 */
	private static LocalDate calculateEasterDay(int year) {

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
	 * Returns  the list of holidays for a given year.
	 * @param year
	 * @return holidays list
	 */
	public static final JSONArray getHolidays(int year) {
		JSONArray items = new JSONArray();
		try {
			listDetails();
			listDates(year);

			final int datesCount = holidayDates.size();
			final int detailsCount = holidayDetails.size();
			if (datesCount == detailsCount) {
				for (int i = 0; i < datesCount; i++) {
					JSONObject rou = new JSONObject();
					String fmtdate = sdf.format(holidayDates.get(i));
					rou.put("date", fmtdate);
					rou.put("title", holidayDetails.get(i));
					items.put(rou);
				}
				for (int i = 0; i < items.length() - 1; i++) {
					int a = i;
					int b = i + 1;
					JSONObject itm0 = items.getJSONObject(a);
					JSONObject itm1 = items.getJSONObject(b);
					String date0 = itm0.getString("date");
					String date1 = itm1.getString("date");
					if (date0.equals(date1)) {
						String title0 = itm0.getString("title");
						String title1 = itm1.getString("title");
						items.getJSONObject(a).put("title",
								title0 + "; " + title1);
						items.remove(b);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}

	/**
	 * Given a year, month, day of month and a day of week, eg
	 * DateTimeConstants.MONDAY, Returns the nearest date with that day of week
	 */
	private static LocalDate getNearestDayOfWeek(int year, int monthOfYear,
			int dayOfMonth, int dayOfWeek) {
		LocalDate t0 = new LocalDate(year, monthOfYear, dayOfMonth);
		return getNearestDayOfWeek(t0, dayOfWeek);
	}

	/**
	 * Given a reference LocalDate and a day of week, eg
	 * DateTimeConstants.MONDAY Returns the nearest date with that day of week
	 */
	private static LocalDate getNearestDayOfWeek(LocalDate t0, int dow) {
		LocalDate t1 = t0.withDayOfWeek(dow);
		LocalDate t2 = t1.isBefore(t0) ? t1.plusWeeks(1) : t1.minusWeeks(1);
		return Math.abs(Days.daysBetween(t1, t0).getDays()) < Math.abs(Days
				.daysBetween(t2, t0).getDays()) ? t1 : t2;
	}

	private static void listDates(int year) {
		LocalDate easterDay = calculateEasterDay(year);

		addHoliday(prepareDate(year, 1, 1));
		addHoliday(getNearestDayOfWeek(year, 1, 6, DateTimeConstants.MONDAY).toDate());
		addHoliday(getNearestDayOfWeek(year, 3, 19, DateTimeConstants.MONDAY).toDate());
		addHoliday(getNearestDayOfWeek(easterDay, DateTimeConstants.SUNDAY).minusWeeks(1).toDate());
		addHoliday(getNearestDayOfWeek(easterDay, DateTimeConstants.THURSDAY).toDate());
		addHoliday(getNearestDayOfWeek(easterDay, DateTimeConstants.FRIDAY).toDate());
		addHoliday(easterDay.toDate());
		addHoliday(prepareDate(year, 5, 1));
		addHoliday(getNearestDayOfWeek(easterDay, DateTimeConstants.MONDAY).plusDays(42).toDate());
		addHoliday(getNearestDayOfWeek(easterDay, DateTimeConstants.MONDAY).plusDays(63).toDate());
		addHoliday(getNearestDayOfWeek(easterDay, DateTimeConstants.MONDAY).plusDays(70).toDate());
		addHoliday(getNearestDayOfWeek(year, 6, 29, DateTimeConstants.MONDAY).toDate());
		addHoliday(prepareDate(year, 7, 20));
		addHoliday(prepareDate(year, 8, 7));
		addHoliday(getNearestDayOfWeek(year, 8, 15, DateTimeConstants.MONDAY).toDate());
		addHoliday(getNearestDayOfWeek(year, 10, 12, DateTimeConstants.MONDAY).toDate());
		addHoliday(getNearestDayOfWeek(year, 11, 1, DateTimeConstants.MONDAY).toDate());
		addHoliday(getNearestDayOfWeek(year, 11, 11, DateTimeConstants.MONDAY).toDate());
		addHoliday(prepareDate(year, 12, 8));
		addHoliday(prepareDate(year, 12, 25));
	}

	private static void listDetails() {
		holidayDetails.add("Año Nuevo");
		holidayDetails.add("Día de los Reyes Magos");
		holidayDetails.add("Día de San José");
		holidayDetails.add("Domingo de Ramos");
		holidayDetails.add("Jueves Santo");
		holidayDetails.add("Viernes Santo");
		holidayDetails.add("Domingo de Resurrección");
		holidayDetails.add("Día del Trabajo");
		holidayDetails.add("Día de la Ascensión");
		holidayDetails.add("Corpus Christi");
		holidayDetails.add("Sagrado Corazón");
		holidayDetails.add("San Pedro y San Pablo");
		holidayDetails.add("Día de la Independencia");
		holidayDetails.add("Batalla de Boyacá");
		holidayDetails.add("La asunción de la Virgen");
		holidayDetails.add("Día de la Raza");
		holidayDetails.add("Todos los Santos");
		holidayDetails.add("Independencia de Cartagena");
		holidayDetails.add("Día de la Inmaculada Concepción");
		holidayDetails.add("Día de Navidad");
	}

	/**
	 * Constructs a Date object using year, month, and the day of that month.
	 * 
	 * @see Date
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	private static Date prepareDate(int year, int monthOfYear, int dayOfMonth) {
		LocalDate nd = new LocalDate(year, monthOfYear, dayOfMonth);
		return nd.toDate();
	}

}
