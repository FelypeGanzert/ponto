package model.entites;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
	
	private final BooleanProperty selected;
	private final StringProperty name;
	private Map<DaysOfWeek, Day> workSchedule  = new HashMap<>();
	private Map<Integer, Day> days  = new HashMap<>();
	
	public Employee() {
		this(null, false);
	}
	
	public Employee(String name) {
		this.selected = new SimpleBooleanProperty(true);
		this.name = new SimpleStringProperty(name);
	}
	
	public Employee(String name, boolean selected) {
		this.selected = new SimpleBooleanProperty(selected);
		this.name = new SimpleStringProperty(name);
	}

	public Employee(String name, Map<DaysOfWeek, Day> workSchedule) {
		this.selected = new SimpleBooleanProperty(false);
		this.name = new SimpleStringProperty(name);
		this.workSchedule = workSchedule;
	}
	
	public BooleanProperty selectedProperty() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}
	
	public boolean isSelected() {
		return this.selected.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public Map<DaysOfWeek, Day> getWorkSchedule() {
		return workSchedule;
	}
	
	public Day getDaySchedule(DaysOfWeek dayOfWeek) {
		if(this.workSchedule.containsKey(dayOfWeek)) {
			return this.workSchedule.get(dayOfWeek);
		}
		return null;
	}

	public void putDaySchedule(DaysOfWeek dayOfWeek, Day day) {
		if(!this.workSchedule.containsKey(dayOfWeek)) {
			this.workSchedule.put(dayOfWeek, day);
		} else {
			throw new DayException("Error: " + dayOfWeek.toString() + " already in work Schedule.");
		}
	}
	
	public void removeDaySchedule(DaysOfWeek dayOfWeek) {
		if(this.workSchedule.containsKey(dayOfWeek)) {
			this.workSchedule.remove(dayOfWeek);
		} else {
			throw new DayException("Error: " + dayOfWeek.toString() + " not found to remove.");
		}
	}
	
	public void changeDaySchedule(DaysOfWeek dayOfWeek, Day day) {
		if(this.workSchedule.containsKey(dayOfWeek)) {
			this.workSchedule.replace(dayOfWeek, day);
		} else {
			throw new DayException("Error: " + dayOfWeek.toString() + " not found to change.");
		}
	}
	
	public Map<Integer, Day> getDays() {
		return days;
	}
	
	public Day getDay(Integer dayOfMonth) {
		if(this.days.containsKey(dayOfMonth)) {
			return this.days.get(dayOfMonth);
		} 
		return null;
	}

	public void putDay(Integer dayOfMonth, Day day) {
		if(!this.days.containsKey(dayOfMonth)) {
			this.days.put(dayOfMonth, day);
		} else {
			throw new DayException("Error: day " + dayOfMonth + " already in.");
		}
	}
	
	public void removeDay(Integer dayOfMonth) {
		if(this.days.containsKey(dayOfMonth)) {
			this.days.remove(dayOfMonth);
		} else {
			throw new DayException("Error: day " + dayOfMonth + " not found to remove.");
		}
	}
	
	public void changeDay(Integer dayOfMonth, Day day) {
		if(this.days.containsKey(dayOfMonth)) {
			this.days.replace(dayOfMonth, day);
		} else {
			throw new DayException("Error: day " + dayOfMonth + " not found to change.");
		}
	}
}
