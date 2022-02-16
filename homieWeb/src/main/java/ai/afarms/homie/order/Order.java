package ai.afarms.homie.order;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.afarms.homie.option.Option;
import ai.afarms.homie.repository.OrderRepository;
import ai.afarms.homie.repository.UserRepository;
import ai.afarms.homie.shippingAddress.ShippingAddress;
import ai.afarms.homie.user.User;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@Document(collection = "order")
public class Order {
	
	@Id
	private String id;
	private String item_no;
	private String item_owner;
	private String orderer;
	private List<Option> option;
	private String addr;
	private String sender_name;
	private String sender_phone;
	private String sender_email;
	private String rec_name;
	private String rec_phone;
	private Date date;
	private int total_price;
	private String item_addr;
	private String payment_id;
	private String status;
	private String receipt;
	
//	public Order(String id, OrderRepository orderRepository) {
//		String pending_id = id + "Long : "+ new Date().getTime();
//		
//		this.id = pending_id;
//		this.item_no = "";
//		this.item_owner = "";
//		this.orderer = "";
//		this.option = new ArrayList<>();
//		this.addr = new ArrayList<>();
//		this.track = new Track();
//		this.date = new Date();
//		this.total_price = 0;
//		this.item_addr ="";
//		
//		orderRepository.save(new Order(pending_id,"","","",new ArrayList<>(), new ArrayList<>(), new Track(), new Date(), 0 , ""));
//	}
	
	public Order(String id, String orderer, String item_no, String item_owner, List<Option> option, String addr, Date date , int total_price, String item_addr) {
		this.id =id;
		this.item_no = item_no;
		this.item_owner = item_owner;
		this.orderer = orderer;
		this.option = option;
		this.addr = addr;
		this.date = date;
		this.total_price = total_price;
		this.item_addr = item_addr;
	}
	public Order(String userId, UserRepository userRepository) {
		userRepository.findById(userId).map(u -> {
			this.orderer = u.getId();
//			this.option = option;
			this.addr = u.getAddr().get(0).getAddr();
			this.date = new Date(System.currentTimeMillis()+32400000);
			return u;
		}).subscribe();
	}
	public Order(String id) {
		this.id = id;
	}
	public Order() {
		// TODO Auto-generated constructor stub
	}
	public Order(String orderId, String receiptUrl) {
		this.id = orderId;
		this.receipt = receiptUrl;
	}

	
}
