package ai.afarms.homie.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ai.afarms.homie.farm.Farm;
import ai.afarms.homie.item.Item;
import ai.afarms.homie.review.Review;
import ai.afarms.homie.review.ReviewList;
import ai.afarms.homie.shippingAddress.ShippingAddress;
import lombok.Data;

@Data
@Document(collection = "user")
public class User implements UserDetails{
	@Id
	private String id;
	private String name;
	private String pw;
	private String phone;
	private List<Farm> farm;
	private List<ShippingAddress> addr;
	private boolean seperator; // 0 is customer, 1 is farmer
	private String nickname;
	private boolean valid;
	private String valid_code;
	private List<String> reviews;
	private boolean membership;
	
	public User(String id, String name, String pw, String phone, List<Farm> farm, List<ShippingAddress> addr, boolean seperator, String nickname, boolean valid, String valid_code, boolean membership) {
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.phone = phone;
		this.farm = farm;
		this.addr = addr;
		this.seperator = seperator;
		this.nickname = nickname;
		this.valid = valid;
		this.valid_code = valid_code;
		this.membership = membership;
	}

	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	
	public User() {
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.getPw();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getId();
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
