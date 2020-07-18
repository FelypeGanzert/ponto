package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.entites.Dates;
import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;

public class EmployeeDaysInScheduleTest {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Employee employee1 = new Employee();
		employee1.setName("Felype");

		// Adding holidays
		Map<Integer, Day> holidays = new HashMap<>();
		holidays.put(10, new Day(null, "feriado", "feriado", "feriado"));
		holidays.put(15, new Day(null, null, null, "feriado"));
		holidays.put(17, new Day(null, "feriado", "feriado", "feriado"));
		holidays.put(20, new Day(null, null, "feriado", null));

		// First we give a Work Schedule to the employee
		employee1.putDaySchedule(DaysOfWeek.SUNDAY, new Day(null, "folga", "folga", "folga"));
		employee1.putDaySchedule(DaysOfWeek.MONDAY, new Day(null, "folga", "folga", "folga"));
		employee1.putDaySchedule(DaysOfWeek.TUESDAY,
				new Day(null, "08:00 as 12:00", "13:30 as 17:30", "18:00 as 21:30"));
		employee1.putDaySchedule(DaysOfWeek.WEDNESDAY,
				new Day(null, "08:00 as 12:00", "13:30 as 18:00", "18:030 as 21:30"));
		employee1.putDaySchedule(DaysOfWeek.THURSDAY, new Day(null, "folga", "13:30 as 17:30", "18:00 as 21:30"));
		employee1.putDaySchedule(DaysOfWeek.FRIDAY, new Day(null, "08:00 as 12:00", "13:30 as 18:30", "folga"));
		employee1.putDaySchedule(DaysOfWeek.SATURDAY, new Day(null, "08:00 as 13:00", "folga", "folga"));

		// base Month
		Date baseDate = sdf.parse("01/07/2020");
		Calendar calDate = Dates.dateToCalendar(baseDate);
		int lastDayOfMonth = calDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		// return one day
		calDate.add(Calendar.DAY_OF_MONTH, -1);

		// Will make a loop to add all the days on the map days with the key being the
		// dayOfMonth
		for (Integer i = 1; i <= lastDayOfMonth; i++) {
			calDate.add(Calendar.DAY_OF_MONTH, 1);
			Date date = Dates.calendarToDate(calDate);
			// get the day of the week
			Integer dayOfWeek = calDate.get(Calendar.DAY_OF_WEEK);
			// get the informatinons of the work schedule by the day of the week
			Day dayToAdd = getDayInformation(holidays.get(i),
					employee1.getDaySchedule(DaysOfWeek.fromInteger(dayOfWeek)));
			dayToAdd.setDay(date);
			// put the day
			employee1.putDay(i, dayToAdd);
		}

		System.out.println("==========================");
		// Ouput employee days
		employee1.getDays().forEach((key, day) -> System.out.println(day));

	}

	public static Day getDayInformation(Day holiday, Day schedule) {
		String morning = schedule.getMorning();
		String afternoon = schedule.getAfternoon();
		String night = schedule.getNight();

		if (holiday != null) {
			String[] holidayInfos = { holiday.getMorning(), holiday.getAfternoon(), holiday.getNight() };
			if (holidayInfos[0] != null) {
				morning = holidayInfos[0];
			}
			if (holidayInfos[1] != null) {
				afternoon = holidayInfos[1];
			}
			if (holidayInfos[2] != null) {
				night = holidayInfos[2];
			}
		}

		Day d = new Day(null, morning, afternoon, night);
		return d;
	}
}