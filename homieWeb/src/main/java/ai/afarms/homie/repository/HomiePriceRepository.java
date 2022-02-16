package ai.afarms.homie.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ai.afarms.homie.priceData.HomiePrice;

public interface HomiePriceRepository extends ReactiveMongoRepository<HomiePrice, String>{

}
