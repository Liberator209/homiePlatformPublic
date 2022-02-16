package ai.afarms.homie.controller;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import ai.afarms.homie.farm.Farm;
import ai.afarms.homie.item.Item;
import ai.afarms.homie.option.Option;
import ai.afarms.homie.order.Order;
import ai.afarms.homie.order.PaymentRequest;
import ai.afarms.homie.order.PaymentRequestService;
import ai.afarms.homie.register.EmailAuthentication;
import ai.afarms.homie.register.Register;
import ai.afarms.homie.register.RegisterService;
import ai.afarms.homie.repository.ItemRepository;
import ai.afarms.homie.repository.ItemTestRepository;
import ai.afarms.homie.repository.OrderRepository;
import ai.afarms.homie.repository.ReviewRepository;
import ai.afarms.homie.repository.UserRepository;
import ai.afarms.homie.review.Review;
import ai.afarms.homie.review.ReviewList;
import ai.afarms.homie.shippingAddress.ShippingAddress;
import ai.afarms.homie.user.ModifyUser;
import ai.afarms.homie.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@org.springframework.stereotype.Controller
public class Controller {
	private ItemRepository itemRepository;
	private ItemTestRepository itemTestRepository;
	private ReviewRepository reviewRepository;
	private RegisterService registerService;
	private UserRepository userRepository;
	private EmailAuthentication emailAuthentication;
	private OrderRepository orderRepository;
	private PaymentRequestService paymentRequestService;
	
	
	public Controller(ItemRepository itemRepository, ReviewRepository reviewRepository, 
			RegisterService registerService, UserRepository userRepository, 
			EmailAuthentication emailAuthentication, OrderRepository orderRepository, 
			PaymentRequestService paymentRequestService, ItemTestRepository itemTestRepository) {
		
		this.itemRepository = itemRepository;
		this.itemTestRepository = itemTestRepository;
		this.reviewRepository = reviewRepository;
		this.registerService = registerService;
		this.userRepository = userRepository;
		this.emailAuthentication = emailAuthentication;
		this.orderRepository = orderRepository;
		this.paymentRequestService = paymentRequestService;
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@GetMapping("/")
	Mono<Rendering> home(Model model, Authentication authentication){
		if(authentication != null) {
			return Mono.just(Rendering.view("index.html")
					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
					.build());
		}
		else
			return Mono.just(Rendering.view("index.html")
					.build());
	}
	
	@GetMapping("/product_view_v2")
	Mono<Rendering> product_view_v2(Model model, Authentication authentication){
		if(authentication != null) {
			return Mono.just(Rendering.view("product_view_v2.html")
					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
					.build());
		}
		else
			return Mono.just(Rendering.view("product_view_v2.html")
					.build());
	}
	
	@GetMapping("/product_list_v2")
	Mono<Rendering> product_list_v2(Model model, Authentication authentication){
		//this.itemTestRepository.findAll().subscribe(System.out::println);
		if(authentication != null) {
			return Mono.just(Rendering.view("shop/product_list_v2.html")
					.modelAttribute("item", this.itemRepository.findAll())
					.modelAttribute("review", this.reviewRepository.findAll())
					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
					.build());
		}
		else
			return Mono.just(Rendering.view("shop/product_list_v2.html")
					.modelAttribute("item", this.itemRepository.findAll())
					.modelAttribute("review", this.reviewRepository.findAll())
					.build());
	}
	
	@GetMapping("/product_list")
	Mono<Rendering> product_list(Model model, Authentication authentication){
		if(authentication != null) {
			return Mono.just(Rendering.view("shop/product_list.html")
					.modelAttribute("item", this.itemRepository.findAll())
					.modelAttribute("review", this.reviewRepository.findAll())
					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
					.build());
		}
		else
			return Mono.just(Rendering.view("shop/product_list.html")
					.modelAttribute("item", this.itemRepository.findAll())
					.modelAttribute("review", this.reviewRepository.findAll())
					.build());
	}
	
	@GetMapping("/product_view")
	Mono<Rendering> product_view(@RequestParam String item_no, @AuthenticationPrincipal UserDetails userInfo, WebSession session, Authentication authentication){
		session.getAttributes().putIfAbsent("item_no", item_no);
		session.getAttributes().replace("item_no", item_no);
		System.out.println(item_no);
		
		if(authentication != null) {
			return Mono.just(Rendering.view("shop/product_view.html")
					.modelAttribute("item_info",
							this.itemRepository.findById(item_no)
							)
					.modelAttribute("review", this.reviewRepository.findById(item_no)
							)
					.modelAttribute("review_count", this.reviewRepository.findById(item_no).map(review -> review.getReview_count())
							)
					.modelAttribute("review_total", this.reviewRepository.findById(item_no).map(review -> review.getReview_score_total())
							)
					.modelAttribute("review_average", this.reviewRepository.findById(item_no).map(review -> review.getReview_score_average())
							)
					.modelAttribute("option", new Option()
							)
					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
					.build());
		}
		else
			return Mono.just(Rendering.view("shop/product_view.html") // farm은 선택시 하나만 가져오도록, farm collection도 만들어서 list<farm>형태로 구축하고, 상품상세에서는 farm 하나만 가져오도록
					.modelAttribute("item_info",
							this.itemRepository.findById(item_no)
							)
					.modelAttribute("review", this.reviewRepository.findById(item_no)
							)
					.modelAttribute("review_count", this.reviewRepository.findById(item_no).map(review -> review.getReview_count())
							)
					.modelAttribute("review_total", this.reviewRepository.findById(item_no).map(review -> review.getReview_score_total())
							)
					.modelAttribute("review_average", this.reviewRepository.findById(item_no).map(review -> review.getReview_score_average())
							)
					.modelAttribute("option", new Option()
							)
					.build());
	}
	@GetMapping("/login")
	Mono<Rendering> login(){
		return Mono.just(Rendering.view("authentication/login.html")
				.build()); 
	}
	
	@GetMapping("/loginfail")
	Mono<Rendering> loginFail(@RequestParam String err){
		if(err.equals("03"))
			return Mono.just(Rendering.view("authentication/needValid.html")
					.build());
		else
			return Mono.just(Rendering.view("authentication/login.html")
					.build()); 
	}
	@GetMapping("/register")
	Mono<Rendering> register(){
		return Mono.just(Rendering.view("authentication/register.html")
				.modelAttribute("Register", new Register())
				.build());
	}
	@PostMapping("/register")
	Mono<Rendering> registerReceive(Model model, @ModelAttribute("Register") Register register){
		if(register.getPhone().length() < 13) {
			int offset=0;
			String newPhone="";
			for(int i=0; i<register.getPhone().length(); ++i) {
				if(i ==2 || i==6) {
					newPhone += register.getPhone().substring(offset,i+1) + "-";
					offset = i+1;
				}
				else if(i==10)
					newPhone += register.getPhone().substring(offset, register.getPhone().length());
			}
			register.setPhone(newPhone);
		}
		return Mono.just(Rendering.view("authentication/registerResult")
				.modelAttribute("Register", register)
				.modelAttribute("code" , this.registerService.mapToUser(register))
				.build());
	}
	
	@PostMapping("/order")
	Mono<Rendering> orderReceive(Model model, @ModelAttribute("option") Option option, Authentication authentication, WebSession session){
		session.getAttributes().putIfAbsent("order", option);
		session.getAttributes().replace("order", option);
		if(authentication != null) {
			String item_no = session.getAttribute("item_no");
			return Mono.just(Rendering.view("order/orderSheet.html")
					.modelAttribute("item_info",
							this.itemRepository.findById(item_no)
							)
					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
					.modelAttribute("option", session.getAttribute("order"))
					.build());
		}
		else {
			session.getAttributes().putIfAbsent("loginRequire", true);
			session.getAttributes().replace("loginRequire", true);
			return Mono.just(Rendering.view("authentication/login.html").build());
		}
	}
	
	@GetMapping("/working")
	Mono<Rendering> working(){
		return Mono.just(Rendering.view("error/Working.html")
				.build()); 
	}
	
//	@PostMapping("/orderRequest")
//	Mono<Rendering> orderRequest(Model model, @ModelAttribute("option") Option option, Authentication authentication){
//		if(authentication != null) {
//			return Mono.just(Rendering.view("order/orderComplete.html")
//					.modelAttribute("item_info",
//							this.itemRepository.findById(item_no)
//							)
//					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
//					.modelAttribute("option", session.getAttribute("order"))
//					.build());
//		}
//		else
//			return Mono.just(Rendering.view("authentication/login.html").build());
//	}
	
	@GetMapping("/order")
	Mono<Rendering> orderRedirect(Model model, Authentication authentication, WebSession session){
		
		if(authentication != null && session.getAttribute("order") !=null && (Boolean)session.getAttribute("loginRequire")) {
			session.getAttributes().replace("loginRequire", false);
			String item_no = session.getAttribute("item_no");
			return Mono.just(Rendering.view("order/orderSheet.html")
					.modelAttribute("item_info",
							this.itemRepository.findById(item_no)
							)
					.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
					.modelAttribute("option", session.getAttribute("order"))
					.build());
		}
		else
			return Mono.just(Rendering.view("redirect:/product_list")
					.build());
	}
	
	@GetMapping("/authentication")
	Mono<Rendering> orderRedirect(Model model, @RequestParam String id, @RequestParam String val) throws UnsupportedEncodingException{
		return Mono.just(Rendering.view("authentication/emailValid.html")
				.modelAttribute("result", this.emailAuthentication.validAuth(id, val))
				.build());
	}
	
	@GetMapping("/myPage")
	Mono<Rendering> myPage(Model model, Authentication authentication, WebSession session){
		return Mono.just(Rendering.view("my/my_main.html")
				.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
				.modelAttribute("orders", this.orderRepository.findByOrderer((String)authentication.getPrincipal()).collectList())

				//.modelAttribute("orderCount", this.orderRepository.getCount((String)authentication.getPrincipal()).subscribe(System.out::println))
				.build());
				
	}
	
	@GetMapping("/review")
	Mono<Rendering> myPageReview(Model model, Authentication authentication, WebSession session){
		return Mono.just(Rendering.view("my/my_review.html")
				.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
				.modelAttribute("orders", this.orderRepository.findByOrderer((String)authentication.getPrincipal()).collectList())
				//.modelAttribute("orderCount", this.orderRepository.getCount((String)authentication.getPrincipal()).subscribe(System.out::println))
				.build());
				
	}
	
	@GetMapping("/orderView")
	Mono<Rendering> orderView(Model model, Authentication authentication, WebSession session, @RequestParam String orderId){
		return Mono.just(Rendering.view("my/my_order_view.html")
				.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
				.modelAttribute("order", this.orderRepository.findById(orderId).filter(order -> order.getOrderer().equals((String)authentication.getPrincipal())))
				.build());
				
	}
	
	
	@GetMapping("/myInfo")
	Mono<Rendering> myInfo(Model model, Authentication authentication, WebSession session){
		return Mono.just(Rendering.view("my/my_info.html")
				.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
				.modelAttribute("ModifyUser", new ModifyUser())
				.build());
				
	}
	
	@PostMapping("/myInfo")
	Mono<Rendering> modifyInfo(Model model, @ModelAttribute("ModifyUser") ModifyUser modifyUser){
		
		return userRepository.findById(modifyUser.getId())
				.filter(user -> passwordEncoder().matches(modifyUser.getPw(),user.getPw()))
				.map(users-> {
					
					if(modifyUser.getPhone().length() < 13) {
						int offset=0;
						String newPhone="";
						for(int i=0; i<modifyUser.getPhone().length(); ++i) {
							if(i ==2 || i==6) {
								newPhone += modifyUser.getPhone().substring(offset,i+1) + "-";
								offset = i+1;
							}
							else if(i==10)
								newPhone += modifyUser.getPhone().substring(offset, modifyUser.getPhone().length());
						}
						modifyUser.setPhone(newPhone);
					}
					
					List<Farm> farm = new ArrayList<>();
					List<ShippingAddress> addr = new ArrayList<>();
					
					ShippingAddress shippingAddress = new ShippingAddress();
					shippingAddress.setName("");
					shippingAddress.setAddr(modifyUser.getShip_zip() + modifyUser.getShip_addr());
					shippingAddress.setAddr_spec(modifyUser.getAddress_detail());
					shippingAddress.setRec_name(modifyUser.getRec_name());
					shippingAddress.setRec_phone(modifyUser.getRec_phone());
					
					addr.add(shippingAddress);
					
					if(!modifyUser.getCh_pw().equals("")) {
						User user = new User(users.getId(), modifyUser.getName(),
								passwordEncoder().encode(modifyUser.getCh_pw()), modifyUser.getPhone(), 
								farm, addr, users.isSeperator(), users.getNickname(), users.isValid(), users.getValid_code(), users.isMembership());
						
						this.userRepository.save(user).subscribe();
					}
					else if(modifyUser.getCh_pw() == null || modifyUser.getCh_pw().trim().isEmpty()) {
						users.setName(modifyUser.getName());
						users.setPhone(modifyUser.getPhone());
						users.setAddr(addr);
						
						this.userRepository.save(users).subscribe();
					}
					return Rendering.view("my/modifyResult.html")
							.modelAttribute("code" , 1)
							.build();
				}).defaultIfEmpty(Rendering.view("my/modifyResult.html").modelAttribute("code" , 0).build());
	}
	
	
	public int sumOfSelected(List<Option> op) {
		int count =0;
		for(Option op_div : op) {
			if(op_div.getOption_amount() != 0)
				count += op_div.getOption_amount() * op_div.getOption_unit();
		}
		return count;
	}
	
	@GetMapping("/orderComplete")
	Mono<Rendering> orderComplete(Model model, Authentication authentication, WebSession session, @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam int amount) throws IOException, InterruptedException{
		System.out.println("주문 완료시 요청 id : " + (String)authentication.getPrincipal());	
		return Mono.just(Rendering.view("order/orderComplete.html")
				.modelAttribute("user",this.userRepository.findById((String)authentication.getPrincipal()))
				.modelAttribute("paymentAPI", 
						this.paymentRequestService.countRemain((String)session.getAttribute("item_no"), (Option)session.getAttribute("order"))
						.then(
								this.paymentRequestService.requestApproval((String)authentication.getPrincipal(), orderId, paymentKey, amount, (Option)session.getAttribute("order"), orderId).map(rs_str -> {
									JSONParser jp = new JSONParser();
									Object obj;
									System.out.println(rs_str);
									try {
										obj = jp.parse(rs_str);
										JSONObject jObj = (JSONObject) obj;
										this.orderRepository.findById(orderId).map(order -> {
											if(((String)jObj.get("method")).equals("카드"))
												order.setReceipt((String)((JSONObject)jObj.get("card")).get("receiptUrl"));
											else
												order.setReceipt((String)((JSONObject)jObj.get("cashReceipt")).get("receiptUrl"));
											
											this.orderRepository.save(order).subscribe();
											return order;
										}).subscribe();
										return jObj;
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									return Mono.just("err");
								}))
				)
				.modelAttribute("orderForm", this.paymentRequestService.countRemain((String)session.getAttribute("item_no"), (Option)session.getAttribute("order"))
						.then(this.paymentRequestService.returnOrder((String)authentication.getPrincipal(), orderId, paymentKey, amount, (Option)session.getAttribute("order"), session.getAttribute("item_no"), session.getAttribute("shipping"))
						.doAfterTerminate(() -> {
							session.getAttributes().remove("order");
						}))
						)
				.build())
				.doOnSuccess(x -> this.itemRepository.findById((String)session.getAttribute("item_no"))
						.subscribe(item -> {
							item.setRemain(item.getRemain() - sumOfSelected(((Option)session.getAttribute("order")).getSelected()));
							if(item.getRemain() < 0)
								item.setRemain(0);
							this.itemRepository.save(item).subscribe();
							}));
				
	}


}