package th.co.locus.jlo.business.customer.bean;

import lombok.Data;

@Data
public class CustomerVerifyData {
	private String name;
	private String key;
	private String ref;
	private String verify;
	private String email;
}
