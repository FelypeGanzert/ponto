package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.entites.Dates;
import model.entites.Day;
import model.entites.Employee;

public class EmployeeDaysTest {

	public static void main(String[] args) throws ParseException {

		Employee employee1 = new Employee();
		employee1.setName("Felype");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// Mês base
		Date d = sdf.parse("01/07/2020");

		Calendar d2 = Dates.dateToCalendar(d);
		int lastDayOfMonth = d2.getActualMaximum(Calendar.DAY_OF_MONTH);
		// return one day
		d2.add(Calendar.DAY_OF_MONTH, -1);
		System.out.println(d2.get(Calendar.DAY_OF_WEEK));

		// Will make a loop to add all the days on the map days with the key being the
		// dayOfMonth
		for (Integer i = 1; i <= lastDayOfMonth; i++) {
			d2.add(Calendar.DAY_OF_MONTH, 1);
			Date date1 = Dates.calendarToDate(d2);
			Day dayToAdd = new Day();
			dayToAdd.setDay(date1);
			dayToAdd.setMorning(d2.get(Calendar.DAY_OF_WEEK) + " - 08:30 às 12:00");
			dayToAdd.setAfternoon(i + " 13:30 às 17:30");
			dayToAdd.setNight("18:00 às 21:30");

			// put day test
			employee1.putDay(i, dayToAdd);
		}

		// remove day test
		employee1.removeDay(10);

		// put day test
		Date newDate = sdf.parse("07/10/2020");
		Day newDay = new Day(newDate, null, null, null);
		employee1.putDay(32, newDay);

		// change day test
		newDate = sdf.parse("07/10/2020");
		newDay = new Day(newDate, null, null, null);
		employee1.changeDay(11, newDay);

		employee1.getDays().forEach((key, day) -> System.out.println(key + " - " + day));
		System.out.println("=============");
		System.out.println(employee1.getDay(31));

	}
}
