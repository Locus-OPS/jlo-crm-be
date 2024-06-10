package th.co.locus.jlo.business.customer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class AddressData extends BaseModelBean {
	private Long addressId;
	private Long customerId;
	private Long memberId;
	private String primaryYn;
    private String addressType;
    private String address;
    private String postCode;
    private String subDistrict;
    private String district;
    private String province;
    private String country;
    
    private String fullAddress;
}
