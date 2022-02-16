package ai.afarms.homie.user;

import org.springframework.stereotype.Component;

import ai.afarms.homie.register.Register;
import ai.afarms.homie.shippingAddress.ShippingAddress;
import lombok.Data;

@Data
@Component
public class ModifyUser {
	private String id;
	private String pw;
	private String ch_pw;
	private String phone;
	private String name;
	private String zip;
	private String address;
	private String address_detail;
	
	private String ship_zip;
	private String ship_addr;
	private String ship_addr_spec;
	private String rec_name;
	private String rec_phone;
}
