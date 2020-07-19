package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.entites.DateUtil;
import model.entites.Day;

public class DayTest {

	public static void main(String[] args) throws ParseException {
		
		Map<Integer, Day> days = new HashMap<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		// M�s base
		Date d = sdf.parse("01/07/2020");
		
		Calendar d2 = DateUtil.dateToCalendar(d);
		int lastDayOfMonth = d2.getActualMaximum(Calendar.DAY_OF_MONTH);
		// return one day
		d2.add(Calendar.DAY_OF_MONTH, -1);
		
		// Will make a loop to add all the days on the map days with the key being the dayOfMonth
		for(Integer i = 1; i <= lastDayOfMonth; i++) {
			d2.add(Calendar.DAY_OF_MONTH, 1);
			Date date1 = DateUtil.calendarToDate(d2);
			Day dayToAdd = new Day();
			dayToAdd.setDay(date1);
			dayToAdd.setMorning("08:30 �s 12:00");
			dayToAdd.setAfternoon(i + " 13:30 �s 17:30");
			dayToAdd.setNight("18:00 �s 21:30");
			days.put(i, dayToAdd);
		}
		
		days.forEach((key, day) -> System.out.println(key + " - " + day));
		
	}

}
