package com.github.marlonlom.colombianholidayscalculator.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.joda.time.DateTimeConstants;

import com.github.marlonlom.colombianholidayscalculator.util.LocalDateUtil;

/**
 * Represents a collection of holiday dates. This enum is typically used to
 * define specific dates that are considered holidays.
 * 
 * @author marlonlom
 * @version 2.0.0
 */
public enum HolidayDates {

	/** Holiday date No. 01. */
	HD01(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.prepareDate(year, 1, 1);
		}
	}),
	/** Holiday date No. 02. */
	HD02(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(year, 1, 6, DateTimeConstants.MONDAY).toDate();
		}
	}),
	/** Holiday date No. 03. */
	HD03(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(year, 3, 19, DateTimeConstants.MONDAY).toDate();
		}
	}),
	/** Holiday date No. 04. */
	HD04(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(dateUtil.calculateEasterDay(year), DateTimeConstants.SUNDAY)
					.minusWeeks(1).toDate();
		}
	}),
	/** Holiday date No. 05. */
	HD05(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(dateUtil.calculateEasterDay(year), DateTimeConstants.THURSDAY).toDate();
		}
	}),
	/** Holiday date No. 06. */
	HD06(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(dateUtil.calculateEasterDay(year), DateTimeConstants.FRIDAY).toDate();
		}
	}),
	/** Holiday date No. 07. */
	HD07(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.calculateEasterDay(year).toDate();
		}
	}),
	/** Holiday date No. 08. */
	HD08(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.prepareDate(year, 5, 1);
		}
	}),
	/** Holiday date No. 09. */
	HD09(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(dateUtil.calculateEasterDay(year), DateTimeConstants.MONDAY)
					.plusDays(42).toDate();
		}
	}),
	/** Holiday date No. 10. */
	HD10(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(dateUtil.calculateEasterDay(year), DateTimeConstants.MONDAY)
					.plusDays(63).toDate();
		}
	}),
	/** Holiday date No. 11. */
	HD11(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(dateUtil.calculateEasterDay(year), DateTimeConstants.MONDAY)
					.plusDays(70).toDate();
		}
	}),
	/** Holiday date No. 12. */
	HD12(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(year, 6, 29, DateTimeConstants.MONDAY).toDate();
		}
	}),
	/** Holiday date No. 13. */
	HD13(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.prepareDate(year, 7, 20);
		}
	}),
	/** Holiday date No. 14. */
	HD14(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.prepareDate(year, 8, 7);
		}
	}),
	/** Holiday date No. 15. */
	HD15(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(year, 8, 15, DateTimeConstants.MONDAY).toDate();
		}
	}),
	/** Holiday date No. 16. */
	HD16(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(year, 10, 12, DateTimeConstants.MONDAY).toDate();
		}
	}),
	/** Holiday date No. 17. */
	HD17(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(year, 11, 1, DateTimeConstants.MONDAY).toDate();
		}
	}),
	/** Holiday date No. 18. */
	HD18(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.getNearestDayOfWeek(year, 11, 11, DateTimeConstants.MONDAY).toDate();
		}
	}),
	/** Holiday date No. 19. */
	HD19(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.prepareDate(year, 12, 8);
		}
	}),
	/** Holiday date No. 20. */
	HD20(new HolidayDateFindable() {
		@Override
		public Date finDate(LocalDateUtil dateUtil, int year) {
			return dateUtil.prepareDate(year, 12, 25);
		}
	});

	/**
	 * Retrieves a {@code HolidayDates} enum constant by its ordinal position.
	 *
	 * @param pos The ordinal position of the desired {@code HolidayDates} constant.
	 * @return An {@link Optional} containing the {@code HolidayDates} constant if
	 *         found, or an empty {@code Optional} if the position is out of bounds.
	 */
	public static Optional<HolidayDates> indexOf(int pos) {
		return Arrays.asList(HolidayDates.values()).stream().filter(hd -> hd.ordinal() == pos).findFirst();
	}

	/**
	 * The strategy used to find the specific date of a holiday. This field is
	 * immutable and must be initialized upon object creation.
	 */
	private final HolidayDateFindable findable;

	/**
	 * Instantiates a new holiday dates.
	 *
	 * @param holidayDateFindable the holiday date findable
	 */
	HolidayDates(HolidayDateFindable holidayDateFindable) {
		findable = holidayDateFindable;
	}

	/**
	 * Retrieves the currently configured {@link HolidayDateFindable} instance.
	 *
	 * @return The {@code HolidayDateFindable} instance used for finding holiday
	 *         dates.
	 */
	public HolidayDateFindable getFindable() {
		return findable;
	}

}
