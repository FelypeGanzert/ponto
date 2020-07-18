package tests;

import java.text.ParseException;

import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;

public class EmployeeWorkScheduleTest {

	public static void main(String[] args) throws ParseException {

		Employee employee1 = new Employee();
		employee1.setName("Felype");

		// Putting days in Work Schedule
		employee1.putDaySchedule(DaysOfWeek.SUNDAY, new Day(null, "folga", "folga", "folga"));
		employee1.putDaySchedule(DaysOfWeek.MONDAY, new Day(null, "folga", "folga", "folga"));
		employee1.putDaySchedule(DaysOfWeek.TUESDAY, new Day(null, "08:00 �s 12:00", "13:30 �s 17:30", "18:00 �s 21:30"));
		employee1.putDaySchedule(DaysOfWeek.WEDNESDAY, new Day(null, "08:00 �s 12:00", "13:30 �s 18:00", "18:030 �s 21:30"));
		employee1.putDaySchedule(DaysOfWeek.THURSDAY, new Day(null, "folga", "13:30 �s 17:30", "18:00 �s 21:30"));
		employee1.putDaySchedule(DaysOfWeek.FRIDAY, new Day(null, "08:00 �s 12:00", "13:30 �s 18:30", "folga"));
		employee1.putDaySchedule(DaysOfWeek.SATURDAY, new Day(null, "08:00 �s 13:00", "folga", "folga"));
		
		// removing a day - ok
		employee1.removeDaySchedule(DaysOfWeek.WEDNESDAY);
		
		// changing a day - ok
		employee1.changeDaySchedule(DaysOfWeek.SUNDAY, new Day(null, "folga", "08:00 �s 12:00", "13:30 �s 18:30"));

		// Printing work Schedule
		employee1.getWorkSchedule().forEach((key, day) -> System.out.println(key.toString() + " - " + day));
		//System.out.println(employee1.getWorkSchedule().get(DaysOfWeek.MONDAY));
		
		System.out.println("=========");
		System.out.println(employee1.getDaySchedule(DaysOfWeek.SATURDAY));
	}
}
