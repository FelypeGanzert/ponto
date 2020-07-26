package db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;
import model.entites.Holiday;

public class DB {
	
	private ObservableList<Employee> employees = FXCollections.observableArrayList();
	private ObservableList<Holiday> holidays = FXCollections.observableArrayList();
	private final String path = "dados.txt";
	
	public DB(ObservableList<Employee> employees) {
		this.employees = employees;
	}
	
	public DB(ObservableList<Employee> employees, ObservableList<Holiday> holidays) {
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
						holidays.add(new Holiday(dayOfMonth, data[1], data[2], data[3]));
						line = br.readLine();
					}
				}
				line = br.readLine();
			}			
		}catch(IOException | ArrayIndexOutOfBoundsException e) {
			throw new DBException("Erro ao ler arquivo com os dados.");
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
	
	private static String holidayToSave(ObservableList<Holiday> holidays) {
		if(holidays.size() == 0) {
			return "";
		}
		String out = "";
		out += "HOLIDAYS START\n";
		for(Holiday h : holidays) {
			out += h.getDayOfMonth() + "#";
			out += h.getMorning() + "#" + h.getAfternoon() + "#" + h.getNight() + "\n";
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

	public ObservableList<Holiday> getHolidays() {
		return holidays;
	}

	public void setHolidays(ObservableList<Holiday> holidays) {
		this.holidays = holidays;
	}
	
}
