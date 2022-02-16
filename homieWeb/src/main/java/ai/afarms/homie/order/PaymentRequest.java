package ai.afarms.homie.order;

import java.util.Date;

import org.springframework.security.authentication.BadCredentialsException;

import ai.afarms.homie.option.Option;
import ai.afarms.homie.repository.ItemRepository;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
public class PaymentRequest {

	private int price;
	private String orderName;
	private String orderer;
	private String orderId;
	private String clientKey;
	private Option option; 
	
	public PaymentRequest(String message) {
		this.price = -1;
		this.orderer = message;
	}

	public PaymentRequest() {
	}
}
