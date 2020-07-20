package db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;

public class DB {
	
	private ObservableList<Employee> employees = FXCollections.observableArrayList();
	private Map<Integer, Day> holidays = new TreeMap<>();
	private final String path = "dados.txt";
	
	public DB(ObservableList<Employee> employees) {
		this.employees = employees;
	}
	
	public DB(ObservableList<Employee> employees, Map<Integer, Day> holidaysRead) {
		this.employees = employees;
		this.holidays = holidays;
	}
	
	public void saveData() {
		try(BufferedWriter bf = new BufferedWriter(new FileWriter(path))){
			for(Employee e : employees) {
				bf.write(employeeAndScheduleToSave(e));
			}
			bf.write(holidayToSave(holidays));
		}catch(IOException e) {
			throw new DBException("Cannot save datas. Error: " + e.getMessage());
		}
	}
	
	public void getData() {
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
						holidays.put(dayOfMonth, new Day(null, data[1], data[2], data[3]));
						line = br.readLine();
					}
				}
				line = br.readLine();
			}			
		}catch(IOException e) {
			throw new DBException("Cannot get data. Error: " + e.getMessage());
		}
	}
	
	private static String employeeAndScheduleToSave(Employee e) {
		String out = "";
		out += "EMPLOYEE START\n";
		out += e.getName() + "\n";
		
		for(int i = 1; i <=7; i++) {
			DaysOfWeek dayOfWeek = DaysOfWeek.fromInteger(i);
			Day day = e.getDaySchedule(dayOfWeek);
			out += day.getMorning() + "#" + day.getAfternoon() + "#" + day.getNight() + "\n";
		}
		
		out += "EMPLOYEE END\n";
		return out;
	}
	
	private static String holidayToSave(Map<Integer, Day> holidays) {
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

	public ObservableList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ObservableList<Employee> employees) {
		this.employees = employees;
	}

	public Map<Integer, Day> getHolidays() {
		return holidays;
	}

	public void setHolidays(Map<Integer, Day> holidays) {
		this.holidays = holidays;
	}
	
	

}
