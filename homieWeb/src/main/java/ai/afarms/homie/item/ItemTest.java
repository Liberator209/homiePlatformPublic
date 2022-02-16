package ai.afarms.homie.item;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.afarms.homie.option.Option;
import ai.afarms.homie.review.Review;
import lombok.Data;

@Data
@Document(collection = "item-test")
public class ItemTest {
	private @Id String id;
	private String owner;
	private List<Option> option;
	private String item_name;
	private int quantity;
	private int remain;
	private Date date;
	private List<Map<String, String>> farm;
	private List<Map<String, String>> spec;
	private String image;
	private Review review;
	private String level;
	
	public ItemTest(String owner, List<Option> option, String item_name, int quantity, int remain, Date date, List<Map<String, String>> farm, List<Map<String, String>> spec){
		this.owner = owner;
		this.option = option;
		this.item_name = item_name;
		this.quantity = quantity;
		this.remain = remain;
		this.date = date;
		this.farm = farm;
		this.spec = spec;
	}
}
