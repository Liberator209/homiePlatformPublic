package ai.afarms.homie.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ai.afarms.homie.user.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
