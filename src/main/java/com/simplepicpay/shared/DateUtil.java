package com.simplepicpay.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

public final class DateUtil {
	private DateUtil() {
		// Empty
	}

	public static int getDate(Date date) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);

		return toReturn.get(Calendar.DATE);
	}

	public static String getMonth(Date date) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);

		return (toReturn.get(Calendar.MONTH) + 1) + "";
	}

	public static String getYear(Date date) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);

		return toReturn.get(Calendar.YEAR) + "";
	}

	public static String getHour(Date date) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);

		return toReturn.get(Calendar.HOUR_OF_DAY) + "";
	}

	public static String getMinute(Date date) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);

		return toReturn.get(Calendar.MINUTE) + "";
	}

	public static Date setHour(Date date, int hour) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);
		toReturn.set(Calendar.HOUR_OF_DAY, hour);

		return toReturn.getTime();
	}

	public static Date setMinute(Date date, int minute) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);
		toReturn.set(Calendar.MINUTE, minute);

		return toReturn.getTime();
	}

	public static Date string2Date(String date, String format) throws ParseException {
		if (StringUtil.isNullOrEmpty(date)) {
			return null;
		}

		return new Date(new SimpleDateFormat(format).parse(date).getTime());
	}

	public static Date string2Date(String date) throws ParseException {
		return string2Date(date, Constants.DATE_FORMAT);
	}

	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}

		return new SimpleDateFormat(format).format(date);
	}

	public static String dateToString(Date date) {
		return dateToString(date, Constants.DATE_FORMAT);
	}

	public static String dateTimeToString(Date date) {
		return dateToString(date, Constants.DATETIME_FORMAT);
	}

	public static Date trunc(Date date) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);
		toReturn.set(Calendar.HOUR_OF_DAY, 00);
		toReturn.set(Calendar.MINUTE, 00);
		toReturn.set(Calendar.SECOND, 00);

		return toReturn.getTime();
	}

	public static Date incrementDay(Date date, int amount) {
		Calendar toReturn = Calendar.getInstance();
		toReturn.setTime(date);
		toReturn.add(Calendar.DATE, amount);

		return toReturn.getTime();
	}

	public static String getStringTime(Date date) {
		String hour = getNormalizedTimeField(getHour(date));
		String minute = getNormalizedTimeField(getMinute(date));

		return hour + ":" + minute;
	}

	private static String getNormalizedTimeField(String hour) {
		if (Integer.parseInt(hour) < 10) {
			hour = "0" + hour;
		}

		return hour;
	}

	public static int dateDiff(Date startDate, Date endDate) {
		GregorianCalendar startCalendar = new GregorianCalendar();
		GregorianCalendar endCalendar = new GregorianCalendar();

		GregorianCalendar curTime = new GregorianCalendar();
		GregorianCalendar baseTime = new GregorianCalendar();

		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);

		int diffMultiplier = 1;

		if (startDate.compareTo(endDate) < 0) {
			baseTime.setTime(endDate);
			curTime.setTime(startDate);
			diffMultiplier = 1;
		} else {
			baseTime.setTime(startDate);
			curTime.setTime(endDate);
			diffMultiplier = -1;
		}
		int yearsResult = 0;
		int monthsResult = 0;
		int daysResult = 0;

		// Get the last day of each month and year (also considering leap year)
		while (curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR)
				|| curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)) {
			int maxDay = curTime.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			monthsResult += maxDay;
			curTime.add(GregorianCalendar.MONTH, 1);
		}

		// Mark as a negative or positive result
		monthsResult = monthsResult * diffMultiplier;

		// Returns the days difference of all months
		daysResult += (endCalendar.get(GregorianCalendar.DAY_OF_MONTH)
				- startCalendar.get(GregorianCalendar.DAY_OF_MONTH));

		return yearsResult + monthsResult + daysResult;
	}

	/**
	 * Returns the minimum value for the day. For example, if the date is
	 * "06/06/1974 15:33:12:299" this method will return a new date such as
	 * "06/06/1974 00:00:00:000".
	 * 
	 * @param date The date.
	 * @return A new date with 0 time.
	 */
	public static Date lowDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		truncDate(calendar); // Remove time information

		return calendar.getTime();
	}

	/**
	 * Returns the maximum value for the day. For example, if the date is
	 * "06/06/1974 15:33:12:299" this method will return a new date such as
	 * "06/06/1974 23:59:59:999".
	 * 
	 * @param date The date.
	 * @return A new date with maximum time.
	 */
	public static Date highDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		truncDate(calendar);
		calendar.roll(Calendar.DATE, true); // Next day
		calendar.roll(Calendar.MILLISECOND, false); // Minus 1 millisecond

		return calendar.getTime();
	}

	/**
	 * Truncates the time portion of the day.
	 *
	 * @param date to be truncated.
	 */
	public static void truncDate(Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}

	public static boolean isValid(String dateToValidate) {
		if (StringUtil.isNullOrEmpty(dateToValidate)) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(StringUtil.onlyNumbers(dateToValidate));
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isDate(String stringDate) {
		if (StringUtil.isNullOrEmpty(stringDate)) {
			return false;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			sdf.setLenient(false);
			sdf.parse(stringDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static int getAge(Date birhDate) {
		return (int) (DateUtil.dateDiff(birhDate, new Date()) / 365.25);
	}

	public static boolean equals(Date d1, Date d2) {
		String s1 = DateUtil.dateToString(d1);
		String s2 = DateUtil.dateToString(d2);

		return s1.equals(s2);
	}

	/**
	 * Returns the number of the day of the week Sat - 1 Mon - 2 Tue - 3 Wed - 4 Thu
	 * - 5 Fri - 6 Sun - 7
	 * 
	 * @param d The date
	 * @return int number representing the number of the day of the week
	 */
	public static int getDayOfWeek(Date d) {
		return LocalDate.parse(DateUtil.dateToString(d), 
				DateTimeFormatter.ofPattern("d/M/uuuu")).getDayOfWeek().getValue();
	}

	public static String getDuration(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		StringBuffer toReturn = new StringBuffer("");
		Period period = Period.between(fromDateTime.toLocalDate(), toDateTime.toLocalDate());
		Duration duration = Duration.between(fromDateTime, toDateTime);

		final int MINUTES_PER_HOUR = 60;
		final int SECONDS_PER_MINUTE = 60;
		final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

		int years = period.getYears();
		int months = period.getMonths();
		int days = period.getDays();
		long seconds = duration.getSeconds();

		long hours = seconds / SECONDS_PER_HOUR;
		long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
		seconds = (seconds % SECONDS_PER_MINUTE);

		toReturn.append("Há ");
		if (years > 0) {
			toReturn.append(years);
			toReturn.append(DateUtil.singularOrPlural(years, " ano", " anos"));
		} else if (months > 0) {
			toReturn.append(months);
			toReturn.append(DateUtil.singularOrPlural(months, " mês", " meses"));
		} else if (days > 0) {
			toReturn.append(days);
			toReturn.append(DateUtil.singularOrPlural(days, " dia", " dias"));
		} else if (hours > 0) {
			toReturn.append(hours);
			toReturn.append(DateUtil.singularOrPlural(hours, " hora", " horas"));
		} else if (minutes > 0) {
			toReturn.append(minutes);
			toReturn.append(DateUtil.singularOrPlural(minutes, " minuto", " minutos"));
		} else if (seconds > 0) {
			toReturn.append(seconds);
			toReturn.append(DateUtil.singularOrPlural(seconds, " segundo", " segundos"));
		}

		return toReturn.toString();
	}

	private static String singularOrPlural(long value, String singular, String plural) {
		if (value <= 1) {
			return singular;
		}

		return plural;
	}

	public static String getDuration(LocalDateTime fromDateTime) {
		return DateUtil.getDuration(fromDateTime, LocalDateTime.now());
	}
	
	public static LocalDate generateRandomDate() {
        int minDay = (int) LocalDate.now().toEpochDay();
        int maxDay = (int) LocalDate.now().plusMonths(6).toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay);
    }
}
