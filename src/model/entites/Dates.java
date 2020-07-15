package model.entites;

import java.util.Calendar;
import java.util.Date;

public class Dates {
	
	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date calendarToDate(Calendar calendar) {
		return calendar.getTime();
	}

}
