package ai.afarms.homie.review;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReviewList {
	private String reviewer;
	private float score;
	private Date date;
	private String body;
	private String ordered_item;
	private List<String> picture;
}
