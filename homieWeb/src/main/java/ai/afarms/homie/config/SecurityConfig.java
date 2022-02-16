package ai.afarms.homie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

import ai.afarms.homie.auth.AuthenticationManager;
import ai.afarms.homie.auth.LoginFailureHandler;


import static org.springframework.security.config.Customizer.withDefaults;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@org.springframework.context.annotation.Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig{
	
	private LoginFailureHandler loginFailureHandler;
	private AuthenticationManager authenticationManager;
	
	public SecurityConfig(LoginFailureHandler loginFailureHandler, AuthenticationManager authenticationManager) {
		this.loginFailureHandler = loginFailureHandler;
		this.authenticationManager = authenticationManager;
	}
	
	public PasswordEncoder passwordEncoder() {
		String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder(11));
		return new DelegatingPasswordEncoder(encodingId, encoders);
	}
//	@Bean
//	public MapReactiveUserDetailsService userDetailsService(Authentication authentication){
//		UserDetails user = User.withUsername((String)authentication.getPrincipal())
//				.password(passwordEncoder().encode((String)authentication.getCredentials()))
//				.roles("USER")
//				.build();
//		return new MapReactiveUserDetailsService(user);
//	}
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) {
		http
		.authorizeExchange()
		.pathMatchers("/assets/**").permitAll()
		.pathMatchers("/", "/product_view", "/product_list", "/login", "/register",
				"/review", "/order", "/checkId", "/logout", "/authentication",
				"/productList_Garakgraph", "/productList_Homiegraph", "/productList_Marketgraph", "/working",
				"/virtual-account", "/product_list_v2")
		.permitAll()
		.pathMatchers("/order", "/myPage","/myPage/**" , "/myInfo", "/payRequest")
		.hasRole("USER")
		.anyExchange()
		.permitAll()
		.and()
		.csrf()
		.disable()
		.formLogin()
		.loginPage("/login")
		.authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/order"))
		.authenticationFailureHandler(loginFailureHandler)
		.and()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutSuccessHandler("/login"))
		.and()
		.authenticationManager(authenticationManager)
		.exceptionHandling()
			.accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.BAD_REQUEST));
	
	return http.build();
	}
	
    public ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
        RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
        successHandler.setLogoutSuccessUrl(URI.create(uri));
        return successHandler;
    }
    
    @Bean
    public AuthenticationWebFilter authenticationWebFilter(
    		AuthenticationManager authenticationManager) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);

        authenticationWebFilter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());

        return authenticationWebFilter;
    }
    
    
    
    
}
