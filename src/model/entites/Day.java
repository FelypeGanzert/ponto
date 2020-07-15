package model.entites;

import java.util.Calendar;
import java.util.Date;

public class Day {

	private Date day;
	private String morning;
	private String afternoon;
	private String night;

	public Day() {
	}

	public Day(Date day, String morning, String afternoon, String night) {
		super();
		this.day = day;
		this.morning = morning;
		this.afternoon = afternoon;
		this.night = night;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getMorning() {
		return morning;
	}

	public void setMorning(String morning) {
		this.morning = morning;
	}

	public String getAfternoon() {
		return afternoon;
	}

	public void setAfternoon(String afternoon) {
		this.afternoon = afternoon;
	}

	public String getNight() {
		return night;
	}

	public void setNight(String night) {
		this.night = night;
	}

	public int getDayOfMonth() {
		return Dates.dateToCalendar(day).get(Calendar.DAY_OF_MONTH);
	}



	@Override
	public String toString() {
		return "Day [" + getDayOfMonth() + ": morning=" + morning + ", afternoon=" + afternoon + ", night=" + night + "]";
	}

}
