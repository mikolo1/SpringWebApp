package mikolo.springWebApp.features.namedays;

import java.time.LocalDate;

public class NameDaysUtils {
	
	private LocalDate now = LocalDate.now();
	private int month = now.getMonthValue();
	private int day = now.getDayOfMonth();

	public String getMonth() {
		return String.valueOf(month);
	}
	
	public String getDay() {
		return String.valueOf(day);
	}
		
	public String getDate() {
		return now.toString();
	}

}
