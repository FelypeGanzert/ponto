package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;

public class ReadingAndSaving {

	public static void main(String[] args) {

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
		
		// Printing work Schedule
		employee1.getWorkSchedule().forEach((key, day) -> System.out.println(key.toString() + " - " + day));
		

		String path = "dados.txt";
		
		// Saving employee data (name, schedule)
		System.out.println("\n\n======================\n\n");
		String out = employeeAndScheduleToSave(employee1);
		System.out.println(out);
		try(BufferedWriter bf = new BufferedWriter(new FileWriter(path, true))){
			bf.write(out);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// Reading and generating employees
		List<Employee> employees = new ArrayList<>();
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
					employees.add(e);
				}
				line = br.readLine();
			}			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// Texting if he gets all the data from dados.txt
		for(Employee e : employees) {
			System.out.println("\n\n");
			System.out.println(employeeAndScheduleToSave(e));
			System.out.println("\n\n");			
		}
		
		
	}
	
	// Logic to generate String to savw
	public static String employeeAndScheduleToSave(Employee e) {
		String out = "";
		out += "EMPLOYEE START\n";
		out += e.getName() + "\n";
		Map<DaysOfWeek, Day> schedule = e.getWorkSchedule();
		for(DaysOfWeek s : schedule.keySet()) {
			Day day = schedule.get(s);
			out += day.getMorning() + "#" + day.getAfternoon() + "#" + day.getNight() + "\n";
		}
		out += "EMPLOYEE END\n";
		return out;
	}
	
}
