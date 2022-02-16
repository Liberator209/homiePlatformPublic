package ai.afarms.homie.priceData;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "garak-price")
public class GarakPrice {
	@Id
	private String id;
	private List<GarakPPD> price;
}
