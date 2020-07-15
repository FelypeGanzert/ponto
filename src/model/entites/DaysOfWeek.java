package model.entites;

public enum DaysOfWeek {

	SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7);
	
	private int value;
	
	DaysOfWeek(int value){
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
	
    public static DaysOfWeek fromInteger(Integer value) {
        for (DaysOfWeek d : DaysOfWeek.values()) {
            if (d.value == value) {
                return d;
            }
        }
        return null;
    }
	
}
