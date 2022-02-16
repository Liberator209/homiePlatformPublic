package ai.afarms.homie.priceData;

import lombok.Data;

@Data
public class HomiePPD {
	private String time;
	private String name;
	private String grade;
	private int unit;
	private int minPrice;
	private int maxPrice;
	private int avgPrice;
	private int yesterDayAvg;
	private int fluctuation;
	private double compareYesterDay;
	private int lastwWeekAvg;
	private double compareLastWeek;
}
