package ai.afarms.homie.priceData;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "market-price")
public class MarketPrice {
	@Id
	private String id;
	private List<MarketPPD> price;
}
