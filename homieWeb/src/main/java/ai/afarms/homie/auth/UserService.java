package ai.afarms.homie.auth;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ai.afarms.homie.repository.UserRepository;
import ai.afarms.homie.user.User;
import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService{
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userRepository.findById(username).switchIfEmpty(Mono.defer(() -> {
			return Mono.error(new UsernameNotFoundException("User not found!!"));
		})).cast(UserDetails.class);
	}

}
