package ai.afarms.homie.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import ai.afarms.homie.user.User;

public class CurrentUserAuthentication extends AbstractAuthenticationToken{
	
	private final User user;

	public CurrentUserAuthentication(User user) {
		super(null);
		this.user = user;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

}
