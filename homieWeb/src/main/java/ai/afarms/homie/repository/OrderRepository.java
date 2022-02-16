package ai.afarms.homie.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ai.afarms.homie.order.Order;
import ai.afarms.homie.order.OrderAggregation;
import ai.afarms.homie.review.ReviewAggregation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveMongoRepository<Order, String>{
	Flux<Order> findByOrderer(String id);
	
//	   @Aggregation({
//		   "{$project: { '$track.status' : 0, count: { $size:'_id' }}}"
//   })
//	Mono<OrderAggregation> getCount(String id);
}
