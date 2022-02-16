package ai.afarms.homie.auth;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import ai.afarms.homie.repository.UserRepository;
import ai.afarms.homie.user.User;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager{
	
	private UserRepository userRepository;
	private UserService userService;


	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	public AuthenticationManager(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication){
		String userId = (String)authentication.getPrincipal();
		String userPwd = (String)authentication.getCredentials();
		User user = new User(userId,userPwd);
		//return userService.findByUsername(userId)
		return userRepository.findById(userId)
				.switchIfEmpty(Mono.error(new BadCredentialsException("01"))) //no user
				.flatMap(mapped -> {
					if(passwordEncoder().matches(user.getPw(), mapped.getPassword())) {
						if(!mapped.isEnabled())
							throw new BadCredentialsException("03"); // no email validated
						else {
							if(mapped.isMembership()) {
								UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userId, userPwd, Arrays.asList(new SimpleGrantedAuthority("ROLE_MEMBER"), new SimpleGrantedAuthority("ROLE_USER")));
								result.setDetails(mapped);
								return Mono.just(result);
							}
							else {
								UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userId, userPwd, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
								result.setDetails(mapped);
								return Mono.just(result);
							}
								
						}
					}
					else
						throw new BadCredentialsException("02"); // password incorrect
				});
	}

}
