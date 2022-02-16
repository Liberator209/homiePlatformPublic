package ai.afarms.homie.auth;

import java.net.URI;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

@Component
public class LoginFailureHandler implements ServerAuthenticationFailureHandler {
	
	private ServerRedirectStrategy redirectStrategy = new org.springframework.security.web.server.DefaultServerRedirectStrategy();

	@Override
	public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, org.springframework.security.core.AuthenticationException e){
		if(e instanceof BadCredentialsException) {
			return redirectStrategy.sendRedirect(webFilterExchange.getExchange(), URI.create("/loginfail?err=" + e.getMessage()));
		}
		return webFilterExchange.getChain().filter(webFilterExchange.getExchange());
	}

}
