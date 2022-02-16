package ai.afarms.homie.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ai.afarms.homie.register.Nickname;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NicknameRepository extends ReactiveMongoRepository<Nickname, String> {

	Flux<Nickname> findFirstByNicknameNotNull(Pageable page);
}
