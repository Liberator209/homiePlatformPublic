package ai.afarms.homie.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ai.afarms.homie.exception.SoldOutException;
import ai.afarms.homie.option.Option;
import ai.afarms.homie.repository.ItemRepository;
import ai.afarms.homie.repository.OrderRepository;
import ai.afarms.homie.repository.UserRepository;
import reactor.core.publisher.Mono;

@Service
public class PaymentRequestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRequestService.class);
	//private String secretKey = "dGVzdF9za19rNmJKWG1nbzI4ZVF5S2VRbWo2OExBbkdLV3g0Og=="; // test key
	private String secretKey = "bGl2ZV9za19PNkJZcTdHV1BWdnlOWjJQazQzTkU1dmJvMWQ0Og=="; //live key

	
	WebClient webClient;
	
	private ItemRepository itemRepository;
	private UserRepository userRepository;
	private OrderRepository orderRepository;
	
	public PaymentRequestService(ItemRepository itemRepository, UserRepository userRepository, OrderRepository orderRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
	}
	
	public Mono<PaymentRequest> returnForm(String clientKey, String customerId, Option option, String item_no, Collection<? extends GrantedAuthority> authorities) {
		return
		itemRepository.findById(item_no).flatMap(item -> {
			PaymentRequest pr = new PaymentRequest();
			int count=0;
			int total_price = 0;
			String order_name = "";
			pr.setClientKey(clientKey);
			pr.setOrderer(customerId);
			pr.setOrderId(customerId.replaceAll("[@.]", "") + Long.toString(new Date().getTime()));
			
			for(Option op : option.getSelected()) {
				for(Option o_op : item.getOption()) {
					System.out.println( "op : " + op.getOption_name() + " item : " + o_op.getOption_name());
					if(op.getOption_name().equals(o_op.getOption_name().replaceAll(" ", ""))) {
						
						if(op.getOption_price() != o_op.getOption_price()) {
							System.out.println("op :" + op.getOption_price());
							System.out.println("o_op:" + o_op.getOption_price());
							LOGGER.error("price hack try captured - " + customerId);
						}
						if(authorities.contains(new SimpleGrantedAuthority("ROLE_MEMBER")))
							total_price += (o_op.getOption_price()-4000) * op.getOption_amount();
						else
							total_price += o_op.getOption_price() * op.getOption_amount();
						if(order_name.equals("")) {
							order_name = o_op.getOption_name() + " " + Integer.toString(op.getOption_amount()) + "개";
						}
						++count;
					}
					
			}
			}
			
			pr.setPrice(total_price);
			
			if(count > 1)
				order_name += " 외 "  + Integer.toString(count-1) + "건";
			
			pr.setOrderName(order_name);
			return Mono.just(pr);
		});
	}
	
	public int sumOfSelected(List<Option> op) {
		int count =0;
		for(Option op_div : op) {
			if(op_div.getOption_amount() != 0)
				count += op_div.getOption_amount() * op_div.getOption_unit();
		}
		return count;
	}
	
	public Mono<String> countRemain(String item_no, Option option ){
		return
				this.itemRepository.findById(item_no).flatMap(item -> {
				if(item.getRemain() < 1 || item.getRemain() < sumOfSelected(option.getSelected())) {
					System.out.println("품절 오류 발생 : " + "주문 수량 : " + Integer.toString(sumOfSelected(option.getSelected())) + " 남은 수량 : " + Integer.toString(item.getRemain()) );
					return Mono.error(new SoldOutException("남은 수량이 선택된 수량 보다 적거나 품절된 상품입니다. " + Integer.toString(sumOfSelected(option.getSelected())) + " 남은 수량 : " + Integer.toString(item.getRemain()) ));
				}
				else {
					return Mono.just("success");
				}
				});
	}
	
	public Mono<String> getOrderView(String orderId){
		webClient = WebClient.create("https://api.tosspayments.com/v1/payments/orders");
		return this.webClient
				.get()
				.uri("/" + orderId)
				.header("Authorization", "Basic " + secretKey)
				.retrieve()
				.bodyToMono(String.class);
			
	}
	
	public Mono<String> requestApproval(String customerId, String orderId, String paymentKey, int amount, Option option, String item_no) throws IOException, InterruptedException{
		
		webClient = WebClient.create("https://api.tosspayments.com/v1/payments");
		return this.webClient
				.post()
				.uri("/" + paymentKey)
				.header("Authorization", "Basic " + secretKey)
				.header("Content-Type", "application/json")
				.bodyValue("{\"amount\":" + amount + ",\"orderId\":\"" + orderId +"\"}")
				.retrieve()
				.bodyToMono(String.class);
			
			
		}
	
	public Mono<Order> returnOrder(String customerId, String orderId, String paymentKey, int amount, Option option, String item_no, Map<String, String> shipping){
		Order order = new Order();
		order.setId(orderId);
		order.setOrderer(customerId);
		order.setDate(new Date(System.currentTimeMillis()+32400000));
		System.out.println(item_no);
		order.setItem_owner(option.getItem_owner());
		order.setTotal_price(amount);
		order.setAddr(shipping.get("zip") + " " + shipping.get("addr") + " " + shipping.get("addr_spec") + " " + shipping.get("ship_request"));
		order.setStatus("수확중");
		option.getSelected().forEach(op -> {
			List<Track> tl = new ArrayList<>();
			for(int i=0; i<op.getOption_amount(); ++i)
				tl.add(new Track());		
			op.setTrackList(tl);
		});
		order.setSender_name(shipping.get("sender_name"));
		order.setSender_email(shipping.get("sender_email"));
		order.setSender_phone(shipping.get("sender_phone"));
		order.setOption(option.getSelected());
		order.setRec_name(shipping.get("rec_name"));
		order.setRec_phone(shipping.get("rec_phone"));
		return this.orderRepository.save(order);
	}
	
}
