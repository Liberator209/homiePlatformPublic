package ai.afarms.homie.option;

import java.util.List;

import ai.afarms.homie.order.Track;
import lombok.Data;

@Data
public class Option {
	private int option_price;
	private int option_amount;
	private String option_name;
	private int option_unit;
	private List<Option> selected;
	private String item_owner;
	private List<Track> trackList;
}
