package ai.afarms.homie;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.bson.types.Binary;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;

import ai.afarms.homie.item.Item;
import ai.afarms.homie.repository.ItemRepository;
import ai.afarms.homie.repository.ReviewRepository;
//import ai.afarms.homie.repository.ItemRepository;
//import ai.afarms.homie.repository.ReviewRepository;
//import ai.afarms.homie.review.Review;
//import ai.afarms.homie.review.ReviewList;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureDataMongo
class HomieWebApplicationTests {
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void itemSaveTest() {
		
//		Logger logger = LoggerFactory.getLogger(HomieWebApplicationTests.class);
//		logger.info("Starting item save test if you want skip, press 's' and enter");
//		
//		Scanner scanner = new Scanner(System.in);
//		if(!scanner.next().equals("s")) {	
//			
//			System.out.println("owener : ");
//			String owner = scanner.next();
//			System.out.println("Enter number of option : ");
//			int option_num = scanner.nextInt();
//			JSONArray options = new JSONArray();
//			IntStream.range(0, option_num).forEachOrdered(n -> {
//				JSONObject jsonObject = new JSONObject();
//				System.out.println("\toption_name : ");
//				String name = scanner.next();
//				System.out.println("\toption_unit (ex. enter 1 if 1kg of 50kg) : ");
//				int unit = scanner.nextInt();
//				System.out.println("\toption_price : ");
//				int price = scanner.nextInt();
//				jsonObject.put("option_name", name);
//				jsonObject.put("option_unit", unit);
//				jsonObject.put("option_price", price);
//				options.add(jsonObject);
//			});
//			System.out.println("item_name : ");
//			String item_name = scanner.next();
//			System.out.println("quantity : ");
//			int quantity = scanner.nextInt();
//			int remain = quantity;
//			System.out.println("date : ");
//			Date date = new Date(System.currentTimeMillis());
//			System.out.println("farm : ");
//			System.out.println("\tnumber of farm : ");
//			List<Map<String, String>> farms = new ArrayList<Map<String,String>>();
//			int farm_count = scanner.nextInt();
//			IntStream.range(0, farm_count).forEachOrdered(n -> {
//				Map<String, String> farm = new HashMap<>();
//				System.out.println("\tname : ");
//				String name = scanner.next();
//				farm.put("name", name);
//				System.out.println("\trtsp : ");
//				String rtsp = scanner.next();
//				farm.put("rtsp", rtsp);
//				System.out.println("\ttype : ");
//				String type = scanner.next();
//				farm.put("type", type);
//				farms.add(farm);
//			});
//			System.out.println("spec : ");
//			List<Map<String, String>> specs = new ArrayList<>();
//			Map<String, String> spec = new HashMap<>();
//			spec.put("body", "sample");
//			specs.add(spec);
//			
//			Item item = new Item(owner, options, item_name, quantity, remain, date, farms, specs);
//			itemRepository.save(item).subscribe();
//			
//			
//		}
//		scanner.close();
//		
	}

	@Test
	void insertReview() throws IOException {
//		String inputFilePath = "C:\\Users\\Furious\\Pictures\\test.jpg";
//		 
//		File inputFile = new File(inputFilePath);
//        byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
//        String encodedString = Base64
//          .getEncoder()
//          .encodeToString(fileContent);
//        
//        Review review = new Review();
//        List<ReviewList> reviewList = new ArrayList<>();
//        
//        ReviewList reviews = new ReviewList();
//        reviews.setDate(new Date(System.currentTimeMillis()));
//        review.setItem_no("61540c0f08ed7233092ad144");
//        List<String> pictures = new ArrayList<>();
//        pictures.add("ASsadsad");
//        reviews.setPicture(pictures);
//        reviews.setReviewer("ddi209");
//        reviews.setBody("감귤이 맛있어서 추가로 구매했어요 감귤이 맛있어서 추가로 구매했어요 감귤이 맛있어서 추가로 구매했어요\n"
//        		+ "감귤이 맛있어서 추가로 했어요 감귤이 맛있어서 추가로 구매했어요");
//        reviews.setScore((float) 4.5);
//        
//        reviewList.add(reviews);
//        review.setReviewList(reviewList);
//        
//        reviewRepository.save(review).subscribe();

	}
	@Test
	void addReview() throws IOException {
//		String inputFilePath = "C:\\Users\\Furious\\Pictures\\test.jpg";
//		 
//		File inputFile = new File(inputFilePath);
//        byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
//        String encodedString = Base64
//          .getEncoder()
//          .encodeToString(fileContent);
//        
//
//        reviewRepository.findById("61540c0f08ed7233092ad144")
//        .flatMap(rv -> {
//        	List<ReviewList> reviewList = new ArrayList<>();
//            ReviewList reviews = new ReviewList();
//            reviews.setDate(new Date(System.currentTimeMillis()));
//            List<String> pictures = new ArrayList<>();
//            pictures.add(encodedString);
//            reviews.setPicture(pictures);
//            reviews.setReviewer("ttak");
//            reviews.setBody("감귤이 맛있어서 추가로 구매했어요 감귤이 맛있어서 추가로 구매했어요 감귤이 맛있어서 추가로 구매했어요\n"
//            		+ "감귤이 맛있어서 추가로 했어요 감귤이 맛있어서 추가로 구매했어요");
//            reviews.setScore((float) 4.5);
//            
//            for(ReviewList existReviews : rv.getReviewList()) {
//            	reviewList.add(existReviews);
//            }
//            reviewList.add(reviews);
//            rv.setReviewList(reviewList);
//        	return reviewRepository.save(rv);
//        }).subscribe();
	}
	
	@Test
	void parseTest() {
		JSONParser jp = new JSONParser();
		Object obj;
		try {
			obj = jp.parse("{\"mId\":\"tvivarepublica\",\"version\":\"1.4\",\"transactionKey\":\"020424CF2B59D08414568CD777B5F3F8\",\"paymentKey\":\"qXJxNkgDKzOEP59LybZ8BaAJnM9bWr6GYo7pRe10BMQwla2v\",\"orderId\":\"liberatorafarmsai1636347011125\",\"orderName\":\"감귤 5kg 7\",\"currency\":\"KRW\",\"method\":\"카드\",\"totalAmount\":154000,\"totalAmountDecimal\":154000.0,\"balanceAmount\":154000,\"balanceAmountDecimal\":154000.0,\"suppliedAmount\":140000,\"suppliedAmountDecimal\":140000.0,\"vat\":14000,\"vatDecimal\":14000.0,\"taxFreeAmount\":0,\"taxFreeAmountDecimal\":0.0,\"status\":\"DONE\",\"requestedAt\":\"2021-11-08T13:50:10+09:00\",\"approvedAt\":\"2021-11-08T13:50:45+09:00\",\"useEscrow\":false,\"cultureExpense\":false,\"card\":{\"company\":\"농협\",\"number\":\"546111******5279\",\"installmentPlanMonths\":0,\"isInterestFree\":false,\"approveNo\":\"00000000\",\"useCardPoint\":false,\"cardType\":\"체크\",\"ownerType\":\"개인\",\"acquireStatus\":\"READY\",\"receiptUrl\":\"https://dashboard.tosspayments.com/sales-slip?transactionId=C5We13j8C%2BVP6caJ8MG3m9pkweKY1wvAM5x9tWveIncRwiRbhHS%2F4hGlfKCS3Q5r&ref=PX\"},\"virtualAccount\":null,\"transfer\":null,\"mobilePhone\":null,\"giftCertificate\":null,\"foreignEasyPay\":null,\"cashReceipt\":null,\"discount\":null,\"cancels\":null,\"secret\":\"ps_0Poxy1XQL8Rnd2xW1aL87nO5Wmlg\",\"type\":\"NORMAL\",\"easyPay\":null}");
			JSONObject jObj = (JSONObject) obj;
			System.out.println(((JSONObject)jObj.get("card")));
			System.out.println((String)((JSONObject)jObj.get("card")).get("receiptUrl"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
