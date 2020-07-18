package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;

public class ReadingAndSaving {

	public static void main(String[] args) {

		List<Employee> generatedEmployees = new ArrayList<>();
		
		Employee employee1 = new Employee();
		employee1.setName("Felype");
		// Putting days in Work Schedule
		employee1.putDaySchedule(DaysOfWeek.SUNDAY, new Day(null, "folga", "folga", "folga"));
		employee1.putDaySchedule(DaysOfWeek.MONDAY, new Day(null, "folga", "folga", "folga"));
		employee1.putDaySchedule(DaysOfWeek.TUESDAY, new Day(null, "08:00 às 12:00", "13:30 às 17:30", "18:00 às 21:30"));
		employee1.putDaySchedule(DaysOfWeek.WEDNESDAY, new Day(null, "08:00 às 12:00", "13:30 às 18:00", "18:030 às 21:30"));
		employee1.putDaySchedule(DaysOfWeek.THURSDAY, new Day(null, "folga", "13:30 às 17:30", "18:00 às 21:30"));
		employee1.putDaySchedule(DaysOfWeek.FRIDAY, new Day(null, "08:00 às 12:00", "13:30 às 18:30", "folga"));
		employee1.putDaySchedule(DaysOfWeek.SATURDAY, new Day(null, "08:00 às 13:00", "folga", "folga"));
		generatedEmployees.add(employee1);
		
		Employee employee2 = new Employee();
		employee2.setName("Carla");
		// Putting days in Work Schedule
		employee2.putDaySchedule(DaysOfWeek.SUNDAY, new Day(null, "folga", "folga", "folga"));
		employee2.putDaySchedule(DaysOfWeek.MONDAY, new Day(null, "cfolga", "folga", "folga"));
		employee2.putDaySchedule(DaysOfWeek.TUESDAY, new Day(null, "c08:00 às 12:00", "13:30 às 17:30", "18:00 às 21:30"));
		employee2.putDaySchedule(DaysOfWeek.WEDNESDAY, new Day(null, "c08:00 às 12:00", "13:30 às 18:00", "18:030 às 21:30"));
		employee2.putDaySchedule(DaysOfWeek.THURSDAY, new Day(null, "cfolga", "13:30 às 17:30", "18:00 às 21:30"));
		employee2.putDaySchedule(DaysOfWeek.FRIDAY, new Day(null, "c08:00 às 12:00", "13:30 às 18:30", "folga"));
		employee2.putDaySchedule(DaysOfWeek.SATURDAY, new Day(null, "c08:00 às 13:00", "folga", "folga"));
		generatedEmployees.add(employee2);
		
		Employee employee3 = new Employee();
		employee3.setName("Eduardo");
		// Putting days in Work Schedule
		employee3.putDaySchedule(DaysOfWeek.SUNDAY, new Day(null, "folga", "folga", "folga"));
		employee3.putDaySchedule(DaysOfWeek.MONDAY, new Day(null, "efolga", "folga", "folga"));
		employee3.putDaySchedule(DaysOfWeek.TUESDAY, new Day(null, "e08:00 às 12:00", "13:30 às 17:30", "18:00 às 21:30"));
		employee3.putDaySchedule(DaysOfWeek.WEDNESDAY, new Day(null, "e08:00 às 12:00", "13:30 às 18:00", "18:030 às 21:30"));
		employee3.putDaySchedule(DaysOfWeek.THURSDAY, new Day(null, "efolga", "13:30 às 17:30", "18:00 às 21:30"));
		employee3.putDaySchedule(DaysOfWeek.FRIDAY, new Day(null, "e08:00 às 12:00", "13:30 às 18:30", "folga"));
		employee3.putDaySchedule(DaysOfWeek.SATURDAY, new Day(null, "e08:00 às 13:00", "folga", "folga"));
		generatedEmployees.add(employee3);
		
		
		// Printing work Schedule
		//employee1.getWorkSchedule().forEach((key, day) -> System.out.println(key.toString() + " - " + day));
		
		// Adding holidays
		Map<Integer, Day> holidays = new TreeMap<>();
		holidays.put(10, new Day(null, "feriado", "feriado", "feriado"));
		holidays.put(15, new Day(null, null, null, "feriado"));
		holidays.put(17, new Day(null, "feriado", "feriado", "feriado"));
		holidays.put(20, new Day(null, null, "feriado", null));

		String path = "dados.txt";
				
		// Saving employee data (name, schedule)
		System.out.println("\n======================\n");
		try(BufferedWriter bf = new BufferedWriter(new FileWriter(path))){
			for(Employee e : generatedEmployees) {
				bf.write(employeeAndScheduleToSave(e));
			}
			bf.write(holidayToSave(holidays));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// Reading and generating employees
		List<Employee> employeesRead = new ArrayList<>();
		Map<Integer, Day> holidaysRead = new TreeMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			// Flag to know if we are reading employee date
			while(line != null) {
				if(line.equals("EMPLOYEE START")) {
					Employee e = new Employee();
					line = br.readLine();
					e.setName(line);
					// Iterate the seven days of week
					for(int i = 1; i <=7; i++) {
						line=br.readLine();
						String[] data = line.split("#");
						e.putDaySchedule(DaysOfWeek.fromInteger(i), new Day(null, data[0], data[1], data[2]));
					}
					employeesRead.add(e);
				}
				if(line.equals("HOLIDAYS START")) {
					boolean readingHoliday = true;
					line = br.readLine();
					while(readingHoliday) {
						if(line.equals("HOLIDAYS END")) {
							readingHoliday = false;
							continue;
						}
						String[] data = line.split("#");
						for(int i = 0; i < data.length; i++) {
							if(data[i].equals("null")) {
								data[i] = null;
							}
						}
						Integer dayOfMonth = Integer.parseInt(data[0]);
						holidaysRead.put(dayOfMonth, new Day(null, data[1], data[2], data[3]));
						line = br.readLine();
					}
				}
				
				line = br.readLine();
			}			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// Texting if he gets all the data from dados.txt
		for(Employee e : employeesRead) {
			System.out.println("===");
			System.out.print(employeeAndScheduleToSave(e));
			System.out.println("===");			
		}		
		
		System.out.println("========");
		System.out.println(holidayToSave(holidaysRead));
		System.out.println("\n\nEnded");
	}
	
	// Logic to generate String to save
	public static String employeeAndScheduleToSave(Employee e) {
		String out = "";
		out += "EMPLOYEE START\n";
		out += e.getName() + "\n";
		Map<DaysOfWeek, Day> schedule = e.getWorkSchedule();
		for(DaysOfWeek key : schedule.keySet()) {
			Day day = schedule.get(key);
			out += day.getMorning() + "#" + day.getAfternoon() + "#" + day.getNight() + "\n";
		}
		out += "EMPLOYEE END\n";
		return out;
	}
	
	public static String holidayToSave(Map<Integer, Day> holidays) {
		if(holidays.size() == 0) {
			return "";
		}
		String out = "";
		out += "HOLIDAYS START\n";
		for(Integer key : holidays.keySet()) {
			out += key + "#";
			Day day = holidays.get(key);
			out += day.getMorning() + "#" + day.getAfternoon() + "#" + day.getNight() + "\n";
		}
		out += "HOLIDAYS END\n";
		return out;
	}
	
}
