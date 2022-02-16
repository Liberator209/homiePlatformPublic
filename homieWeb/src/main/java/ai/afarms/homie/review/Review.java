package ai.afarms.homie.review;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "review")
public class Review {
	@Id
	private String item_no;
	private List<ReviewList> reviewList;
	private int review_score_total;
	private double review_score_average;
	private int review_count;
}
