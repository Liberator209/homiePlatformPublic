package ai.afarms.homie.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ai.afarms.homie.priceData.GarakPrice;

public interface GarakPriceRepository extends ReactiveMongoRepository<GarakPrice, String>{

}
