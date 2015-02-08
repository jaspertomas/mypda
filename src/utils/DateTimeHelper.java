package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {
	static SimpleDateFormat dateformat = new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss"));
	public static String toString(Date date)
	{
		return dateformat.format(date);
	}
	public static Date toDate(String datestring)
	{
		try {
			return dateformat.parse(datestring);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	//from https://androidsnippets.wordpress.com/2012/08/28/how-to-get-total-number-of-days-in-a-month-in-java-android/
	public static int getDaysInMonth(int monthNumber, int year) {
		int days = 0;
		if (monthNumber >= 0 && monthNumber < 12) {
			try {
				Calendar calendar = Calendar.getInstance();
				int date = 1;
				calendar.set(year, monthNumber, date);
				days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			} catch (Exception e) {
				if (e != null)
					e.printStackTrace();
			}
		}
		return days;
	}
	public static Date addDays(Date date,int daysToAdd)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, daysToAdd);
		return c.getTime();
	}
	public static Integer getYear(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	public static Integer getMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}
	public static Integer getDayOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
}
