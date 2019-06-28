package automation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;

public class DateUtil {

	static Calendar cal;
	static SimpleDateFormat s;
	String[] arr;

	public static String converttimestamp(Long unixSeconds) {
		Date date;
		date = new Date(unixSeconds);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the
																				// format
																				// of
																				// your
																				// date
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static String getDateWithNDaysBefore(int noOFDays) {
		System.out.println("No of days : "+noOFDays);
		String dateArray[] = DateUtil.getPreviousDate("day", noOFDays);
		return (dateArray[0] + "-" + dateArray[1] + "-" + dateArray[2]);
	}
	
	public static String getDateWithNDaysBeforewithdifferentFormat(int noOFDays) {
		String dateArray[] = DateUtil.getPreviousDatewithDDMMYYYYFormat("day", noOFDays);
		return (dateArray[0] + "/" + dateArray[1] + "/" + dateArray[2]);
	}

	public static String converttimestamp(String unixSeconds) {
		return converttimestamp(Long.parseLong(unixSeconds));
	}

	public static String[] getNextDate(String dateModule, int frequency) {
		cal = Calendar.getInstance();
		s = new SimpleDateFormat("yyyyMMdd");
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DATE, frequency);
		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, frequency);
		} else {
			cal.add(Calendar.YEAR, frequency);
		}
		String result = s.format(new Date(cal.getTimeInMillis()));
		String year = result.substring(0, 4);
		String month = result.substring(4, 6);
		String day = result.substring(6, 8);
		String[] date = { year, month, day };
		return date;
	}

	public static String getNextDateFromGivenDate(String curDate) {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return format.format(calendar.getTime());
	}
	
	public static String getNextDateFromGivenDateE(String curDate) {
		final SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
		Date date = null;
		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return format.format(calendar.getTime());
	}

	public static String getNextDateFromGivenDateForGivenDays(String curDate,
			int numberOfDays) {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
		return format.format(calendar.getTime());
	}

	public static String getNextDateFromGivenDateFoGivenDateModule(
			String dateFormat, String dateModule, String curDate, int frequency) {
		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal = Calendar.getInstance();
		cal.setTime(date);
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DAY_OF_YEAR, frequency);

		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, frequency);

		} else {
			cal.add(Calendar.YEAR, frequency);
		}
		
		System.out.println("This is the start date 1 passed end return : "+cal.getTime());
		return format.format(cal.getTime());
	}
	
	public static String getNextDateFromGivenDateWithoutZero(String dateModule, String curDate, int frequency) {
		String dateFormat="dd";
		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal = Calendar.getInstance();
		cal.setTime(date);
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DAY_OF_YEAR, frequency);

		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, frequency);

		} else {
			cal.add(Calendar.YEAR, frequency);
		}
		
		System.out.println("This is the start date 1 passed end return : "+cal.getTime());
		String date1=format.format(cal.getTime());
		if(date1.startsWith("0"))
			date1=date1.substring(1);
		return date1;
		
	}


	public static String getPreviousDateFromGivenDateFoGivenDateModule(
			String dateFormat, String dateModule, String curDate, int frequency) {
		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal = Calendar.getInstance();
		cal.setTime(date);
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DAY_OF_YEAR, -frequency);

		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, -frequency);

		} else {
			cal.add(Calendar.YEAR, -frequency);
		}
		return format.format(cal.getTime());
	}

	public static String getPreviousDateForGivenDateModuleInGivenFormat(
			String dateFormat, String dateModule, int frequency) {
		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		cal = Calendar.getInstance();
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DAY_OF_YEAR, -frequency);

		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, -frequency);

		} else {
			cal.add(Calendar.YEAR, -frequency);
		}
		return format.format(cal.getTime());
	}

	public static String getNextDateForGivenDateModuleInGivenFormat(
		String dateFormat, String dateModule, int frequency) {
		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
//		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		cal = Calendar.getInstance();
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DAY_OF_YEAR, frequency);

		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, frequency);

		} else {
			cal.add(Calendar.YEAR, frequency);
		}
		return format.format(cal.getTime());
	}
	
	public static String getNextDateForGivenDateModuleInGivenFormatt(
			String dateFormat, String dateModule, int frequency) {
//			final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			final SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
			cal = Calendar.getInstance();
			if (dateModule.equalsIgnoreCase("day")) {
				cal.add(Calendar.DAY_OF_YEAR, frequency);

			} else if (dateModule.equalsIgnoreCase("month")) {
				cal.add(Calendar.MONTH, frequency);

			} else {
				cal.add(Calendar.YEAR, frequency);
			}
			return formatter.format(cal.getTime());
		}

	public static String[] getPreviousDate(String dateModule, int frequency) {
		cal = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DATE, -frequency);
		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, -frequency);
		} else {
			cal.add(Calendar.YEAR, -frequency);
		}
		String result = s.format(new Date(cal.getTimeInMillis()));
		String year = result.substring(0, 4);
		String month = result.substring(4, 6);
		String day = result.substring(6, 8);
		String[] date = { year, month, day };
		return date;
	}
	
	public static String[] getPreviousDatewithDDMMYYYYFormat(String dateModule, int frequency) {
		cal = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("ddMMyyyy");
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DATE, -frequency);
		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, -frequency);
		} else {
			cal.add(Calendar.YEAR, -frequency);
		}
		String result = s.format(new Date(cal.getTimeInMillis()));
		/*String year = result.substring(0, 4);
		String month = result.substring(4, 6);
		String day = result.substring(6, 8);*/
		String day = result.substring(0, 2);
		String month = result.substring(2, 4);
		String year = result.substring(4, 8);
		String[] date = { day, month, year };
		return date;
	}

	public String[] getDate(String dateText) {
		arr = new String[3];
		if (dateText.equalsIgnoreCase("Over 1 year ago")) {
			return getPreviousDate("year", 2);
		} else if (dateText.equalsIgnoreCase("Within past year")) {
			return getPreviousDate("year", 1);
		} else if (dateText.equalsIgnoreCase("Within 30 days from now")) {
			return getNextDate("day", 20);
		} else if (dateText.equalsIgnoreCase("Over 30 days from now")) {
			return getNextDate("month", 2);
		} else if (dateText.equalsIgnoreCase("NA")) {
			Reporter.log("Step : date value is NA in data sheet\n");
			return null;
		} else {
			Reporter.log("Step : date value in data sheet is invalid\n");
		}
		return null;

	}

	public static String getCurrentdateInStringWithGivenFormate(String formate) {
		String date = new SimpleDateFormat(formate).format(new Date());		
		return date;
	}
	
	public static String getCurrentdateInStringWithoutZero(String formate) {
		String date = new SimpleDateFormat(formate).format(new Date());
		if(date.startsWith("0"))
			date=date.substring(1);
		return date;
	}

	public static String dateConversionForGivenPattern(String startDate,
			String pattern) {
		SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdfSource.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdfDestination = new SimpleDateFormat(pattern);
		return sdfDestination.format(date);
	}

	public static String dateConversion(String fromFormat, String toFormat,
			String startDate) {
		SimpleDateFormat sdfSource = new SimpleDateFormat(fromFormat);
		Date date = null;
		try {
			date = sdfSource.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdfDestination = new SimpleDateFormat(toFormat);
		return sdfDestination.format(date);
	}

	public static String addHour(String hour1, String hour2) {
		int totalSeconds = (Integer.parseInt(hour1.split(":")[0]) + Integer
				.parseInt(hour2.split(":")[0]))
				* 3600
				+ (Integer.parseInt(hour1.split(":")[1]) + Integer
						.parseInt(hour2.split(":")[1])) * 60;
		int hours = totalSeconds / 3600;
		int minutes = ((totalSeconds) / 60) % 60;
		if (minutes < 10)
			return hours + ":0" + minutes;
		return hours + ":" + minutes;
	}

	public boolean isFirstDateIsBeforeSecondDate(String format,
			String date1string, String date2string) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat(format).parse(date1string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat(format).parse(date2string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1.before(date2);
	}

	public boolean isFirstDateIsAfterSecondDate(String format,
			String date1string, String date2string) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat(format).parse(date1string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat(format).parse(date2string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1.after(date2);
	}

	public String getDayOfMonthSuffix(int n) {
		int dayOfMonth = Integer.parseInt(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("dd", "day",
						getCurrentdateInStringWithGivenFormate("dd"), n));
		if (dayOfMonth >= 11 && dayOfMonth <= 13) {
			return "th";
		}
		switch (dayOfMonth % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public static String convertHourIntoDecimalFormat(String hour) {
		String arr[] = hour.split(":");
		String hourInDecimalFormat = "";
		if (arr.length == 3)
			hourInDecimalFormat = Double.parseDouble(arr[0])
					+ Double.parseDouble(arr[1]) / 60
					+ Double.parseDouble(arr[2]) / 3600 + "";
		else if (arr.length == 2)
			hourInDecimalFormat = Double.parseDouble(arr[0])
					+ Double.parseDouble(arr[1]) / 60 + "";
		else {
			Assert.fail("Wrong Date Format !!");
		}
		if (hourInDecimalFormat.length() - hourInDecimalFormat.indexOf('.') - 1 == 1)
			return hourInDecimalFormat + "0";
		return hourInDecimalFormat;
	}

	public int getNumberOfDaysInMonthWhichHaveGivenmDate(String date) {
		LocalDate date1 = LocalDate.of(Integer.parseInt(DateUtil
				.dateConversion("yyyy-MM-dd", "yyyy", date)), Integer
				.parseInt(DateUtil.dateConversion("yyyy-MM-dd", "MM", date)),
				Integer.parseInt(DateUtil.dateConversion("yyyy-MM-dd", "dd",
						date)));
		return date1.lengthOfMonth();
	}

	public int monthDifferenceBetweenFirstAndSecondDate(String firstDate,
			String secondDate) {

		return Integer.parseInt(DateUtil.dateConversion("yyyy-MM-dd", "MM",
				firstDate))
				- Integer.parseInt(DateUtil.dateConversion("yyyy-MM-dd", "MM",
						secondDate));

	}

	public static int daysDifferenceBetweenFirstAndSecondDate(String firstDate,
			String secondDate, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		System.out.println("First Date as :: " + firstDate
				+ " and Second Date as :: " + secondDate);
		Date d1 = null;
		Date d2 = null;
		long diffDays = 0;

		try {
			d1 = format.parse(firstDate);
			d2 = format.parse(secondDate);
			long diff = d2.getTime() - d1.getTime();
			diffDays = diff / (24 * 60 * 60 * 1000) + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Day Difference is "+diffDays);
		return (int) diffDays;

	}

	static boolean isGivenDateIsPresentIndayOffList(String date) {
		List<String> list = new ArrayList<>();
		list.add("2017-03-13");
		for (String value : list) {
			if (value.equalsIgnoreCase(date))
				return true;
		}
		return false;
	}

	public static String getPreviousDateWithNoOff(String date) {
		String day;
		date = DateUtil.getPreviousDateFromGivenDateFoGivenDateModule(
				"yyyy-MM-dd", "day", date, 1);
		day = DateUtil.dateConversion("yyyy-MM-dd", "EEEE", date);
		while (day.equalsIgnoreCase("Saturday")
				|| day.equalsIgnoreCase("Sunday")
				|| isGivenDateIsPresentIndayOffList(date)) {
			date = DateUtil.getPreviousDateFromGivenDateFoGivenDateModule(
					"yyyy-MM-dd", "day", date, 1);
			day = DateUtil.dateConversion("yyyy-MM-dd", "EEEE", date);
		}
		return date;
	}

	
}
