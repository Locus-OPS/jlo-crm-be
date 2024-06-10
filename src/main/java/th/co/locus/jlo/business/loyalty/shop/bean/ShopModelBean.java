package th.co.locus.jlo.business.loyalty.shop.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShopModelBean extends BaseModelBean {

	private Long shopId;
	private String shopNo;
	private String shopName;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long shopTypeId;

	private String shopType;
	private String shopTypeName;

	private String locationId;
	private String locationName;

	private String activeFlag;
	
	private Long programId;

//	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
//	private LocalDateTime createdDate;
//	private String createdBy;
//
//	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
//	private LocalDateTime updatedDate;
//	private String updatedBy; 
	
}
