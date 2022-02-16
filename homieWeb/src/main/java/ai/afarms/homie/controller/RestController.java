package ai.afarms.homie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ai.afarms.homie.exception.NotValidRequest;
import ai.afarms.homie.order.PaymentRequest;
import ai.afarms.homie.order.PaymentRequestService;
import ai.afarms.homie.priceData.GarakPrice;
import ai.afarms.homie.register.Register;
import ai.afarms.homie.repository.GarakPriceRepository;
import ai.afarms.homie.repository.HomiePriceRepository;
import ai.afarms.homie.repository.ItemRepository;
import ai.afarms.homie.repository.MarketPriceRepository;
import ai.afarms.homie.repository.OrderRepository;
import ai.afarms.homie.repository.ReviewRepository;
import ai.afarms.homie.repository.UserRepository;
import ai.afarms.homie.review.Review;
import ai.afarms.homie.shippingAddress.ShippingAddress;
import ai.afarms.homie.user.ModifyUser;
import ai.afarms.homie.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	//private String clientKey = "test_ck_BE92LAa5PVbX0ENmjwR87YmpXyJj"; // PG test key
	private String clientKey = ""; // PG live key
	
	//private String secretKey = "dGVzdF9za19rNmJKWG1nbzI4ZVF5S2VRbWo2OExBbkdLV3g0Og=="; // PG test key
	private String secretKey = ""; //PG live key
	
	private UserRepository userRepository;
	private ReviewRepository reviewRepository;
	private ItemRepository itemRepository;
	private OrderRepository orderRepository;
	private GarakPriceRepository garakPriceRepository;
	private HomiePriceRepository homiePriceRepository;
	private MarketPriceRepository marketPriceRepository;
	
	private PaymentRequestService paymentRequestService;
	
	
	RestController(UserRepository userRepository, ReviewRepository reviewRepository,
			ItemRepository itemRepository, PaymentRequestService paymentRequestService,
			OrderRepository orderRepository, GarakPriceRepository garakPriceRepository,
			HomiePriceRepository homiePriceRepository, MarketPriceRepository marketPriceRepository){
		
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
		this.itemRepository = itemRepository;
		this.orderRepository = orderRepository;
		this.paymentRequestService = paymentRequestService;
		this.garakPriceRepository = garakPriceRepository;
		this.homiePriceRepository = homiePriceRepository;
		this.marketPriceRepository = marketPriceRepository;
	}

	
	@PostMapping("/checkId")
	Mono<String> checkEmail(Model model, @RequestBody String inputId){
		
		return this.userRepository.findById(inputId)
						.map(exist -> {
							return "dup";
						})
						.defaultIfEmpty("success");
				}
	@PostMapping("/payRequest")
	Mono<PaymentRequest> payRequest(Model model, Authentication authentication, WebSession session, @RequestBody Map<String, String> shipping){
		session.getAttributes().putIfAbsent("shipping", shipping);
		session.getAttributes().replace("shipping", shipping);
		System.out.println("쉬핑 :: ? ");
		System.out.println(shipping);
		return this.paymentRequestService.returnForm(clientKey, (String)authentication.getPrincipal(), session.getAttribute("order"), session.getAttribute("item_no"), authentication.getAuthorities()).onErrorResume(error -> Mono.just(new PaymentRequest(error.getMessage())));
	}
	
	@PostMapping("/virtual-account")
	void virtualAccount(@RequestBody Map<String, String> status){
		System.out.println("State change");
		System.out.println("Secret : " + status.get("secret"));
		System.out.println("status : " + status.get("status"));
		System.out.println("orderId : " + status.get("orderId"));

	}
	
	@PostMapping("/getOrderView")
	Mono<JSONObject> getOrder(Model model, Authentication authentication, WebSession session, @RequestBody Map<String, String> orderId){
		return this.orderRepository.findById((String)orderId.get("orderId")).filter(order -> order.getOrderer().equals((String)authentication.getPrincipal())).switchIfEmpty(Mono.error(new NotValidRequest("잘못된 요청"))).then(
				this.paymentRequestService.getOrderView((String)orderId.get("orderId")).map(rs_str -> {
					JSONParser jp = new JSONParser();
					Object obj;
					System.out.println(rs_str);
					try {
						obj = jp.parse(rs_str);
						JSONObject jObj = (JSONObject) obj;
						return jObj;

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return new JSONObject();
				}));
	}
	
	@GetMapping("/productList_Garakgraph")
	Mono<String> getGarakPrice(){
		return this.garakPriceRepository.findById("감귤-보통").map(garakPrice -> {
			String toJson = new Gson().toJson(garakPrice.getPrice());
			return toJson;
		});
	}
	
	@GetMapping("/productList_Homiegraph")
	Mono<String> getHomiePrice(){
		return this.homiePriceRepository.findById("감귤").map(homiePrice -> {
			String toJson = new Gson().toJson(homiePrice.getPrice());
			return toJson;
		});
	}
	
	@GetMapping("/productList_Marketgraph")
	Mono<String> getMarketPrice(){
		return this.marketPriceRepository.findById("감귤").map(marketPrice -> {
			String toJson = new Gson().toJson(marketPrice.getPrice());
			return toJson;
		});
	}
	
}

