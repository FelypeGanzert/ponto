package model.entites;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Holiday extends Day{
	
	
	private final BooleanProperty selected;
	private final IntegerProperty dayOfMonth;
	private final StringProperty name;
	
	
	public Holiday() {
		this.selected = new SimpleBooleanProperty(false);
		this.dayOfMonth = new SimpleIntegerProperty();
		this.name = new SimpleStringProperty();
	}
	
	public Holiday(Integer dayOfMonth) {
		this(dayOfMonth, false);
	}
	
	public Holiday(Integer dayOfMonth, String morning, String afternoon, String night) {
		super(null, morning, afternoon, night);
		this.selected = new SimpleBooleanProperty(false);
		this.dayOfMonth = new SimpleIntegerProperty(dayOfMonth);
		this.name = new SimpleStringProperty("");
		updateName();
	}
	
	public Holiday(Integer dayOfMonth, boolean selected) {
		this.selected = new SimpleBooleanProperty(selected);
		this.dayOfMonth = new SimpleIntegerProperty(dayOfMonth);
		this.name = new SimpleStringProperty("");
		updateName();
	}
	
	public StringProperty getNameProperty() {
		return name;
	}
	
	public void updateName() {
		String name = "";
		
		if(getMorning() != null) {
			name += getMorning();
		} else {name += "-";}
		name += "/";
		
		if(getAfternoon() != null) {
			name += getAfternoon();
		} else {name += "-";}
		name += "/";
		
		if(getNight() != null) {
			name += getNight();
		} else {name += "-";}
		
		this.name.set(name);
	}

	public BooleanProperty getSelectedProperty() {
		return selected;
	}
	
	public boolean isSelected() {
		return selected.get();
	}
	
	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}

	public IntegerProperty getDayOfMonthProperty() {
		return dayOfMonth;
	}
	
	public Integer getDayOfMonth() {
		return this.dayOfMonth.get();
	}
	
	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth.set(dayOfMonth);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayOfMonth == null) ? 0 : dayOfMonth.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Holiday other = (Holiday) obj;
		if (dayOfMonth == null) {
			if (other.dayOfMonth != null)
				return false;
		} else if (!dayOfMonth.equals(other.dayOfMonth))
			return false;
		return true;
	}
	
	

}
