package ai.afarms.homie.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ai.afarms.homie.priceData.MarketPrice;

public interface MarketPriceRepository extends ReactiveMongoRepository<MarketPrice, String>{

}
