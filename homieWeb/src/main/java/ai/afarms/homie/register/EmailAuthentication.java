package ai.afarms.homie.register;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.afarms.homie.farm.Farm;
import ai.afarms.homie.repository.UserRepository;
import ai.afarms.homie.shippingAddress.ShippingAddress;
import ai.afarms.homie.user.User;
import reactor.core.publisher.Mono;

@Service
public class EmailAuthentication {
	private UserRepository userRepository;
	
	public EmailAuthentication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Mono<Integer> validAuth(String id, String valid_code) throws UnsupportedEncodingException {
		String d_id = URLDecoder.decode(id, "UTF-8");
		String d_valid_code = URLDecoder.decode(valid_code, "UTF-8");
		
		return this.userRepository.findById(d_id)
				.filter(idMatched -> idMatched.getValid_code().equals(d_valid_code))
				.map(Exist -> {
					Exist.setValid(true);
					this.userRepository.save(Exist).subscribe();
					return 1;
				})
				.defaultIfEmpty(0);
		}
}
