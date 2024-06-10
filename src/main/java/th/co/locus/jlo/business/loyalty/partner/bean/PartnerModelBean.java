package th.co.locus.jlo.business.loyalty.partner.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class PartnerModelBean extends SortingModelBean {

	private Long partnerId;
	private String partner;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long partnerTypeId;
	private String partnerType;
	private String partnerNo;
	private String activeFlag;
	
	private Date createdDate;
	private String createdBy;
	
	private Date updatedDate;
	private String updatedBy; 
	private Integer buId;
}
