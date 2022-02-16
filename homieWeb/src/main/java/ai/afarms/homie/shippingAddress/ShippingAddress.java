package ai.afarms.homie.shippingAddress;

import lombok.Data;

@Data
public class ShippingAddress {
	private String name;
	private String addr;
	private String addr_spec;
	private String rec_name;
	private String rec_phone;
}
