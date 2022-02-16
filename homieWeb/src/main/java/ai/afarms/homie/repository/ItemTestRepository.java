package ai.afarms.homie.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ai.afarms.homie.item.ItemTest;
import reactor.core.publisher.Mono;

public interface ItemTestRepository extends ReactiveCrudRepository<ItemTest, String> {
	public Mono<ItemTest> findById(String id);
}
