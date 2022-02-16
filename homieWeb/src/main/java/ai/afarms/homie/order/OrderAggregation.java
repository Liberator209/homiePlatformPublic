package ai.afarms.homie.order;

import org.springframework.data.annotation.Id;

import ai.afarms.homie.review.ReviewAggregation;
import lombok.Data;

@Data
public class OrderAggregation {
		@Id
		private String id;
		private int status;
		private int count;
		
}
