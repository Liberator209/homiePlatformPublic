package ai.afarms.homie.review;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class ReviewAggregation {
	@Id
	private String id;
	private int count;
	private float sum;
	private int average;
	
}
