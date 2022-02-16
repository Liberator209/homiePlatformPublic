package ai.afarms.homie.register;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Register {
	private String id;
	private String pw;
	private String phone;
	private String name;
	private String zip;
	private String address;
	private String address_detail;
}
