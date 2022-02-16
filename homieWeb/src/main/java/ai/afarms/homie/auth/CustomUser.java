package ai.afarms.homie.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ai.afarms.homie.farm.Farm;
import ai.afarms.homie.shippingAddress.ShippingAddress;

public class CustomUser implements UserDetails{
	
	private String id;
	private String name;
	private String pw;
	private String phone;
	private List<Farm> farm;
	private List<ShippingAddress> addr;
	private boolean seperator; // 0 is customer, 1 is farmer
	private String nickname;
	private boolean valid;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		String AUTHORITY;
		if(seperator)
			AUTHORITY = "ROLE_FARMER";
		else
			AUTHORITY = "ROLE_CUSTOMER";
		
        auth.add(new SimpleGrantedAuthority(AUTHORITY));
        return auth;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pw;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return valid;
	}

}
