package ai.afarms.homie.priceData;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "homie-price")
public class HomiePrice {
	@Id
	private String id;
	private List<HomiePPD> price;
}
