package ai.afarms.homie.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ai.afarms.homie.item.Item;
import reactor.core.publisher.Mono;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {
	public Mono<Item> findById(String id);
}
