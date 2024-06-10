package th.co.locus.jlo.business.loyalty.program.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocationBasePointModelBean extends SortingModelBean {
	
	private Long tierId;
	private Long locationBasePointId;
	
	private String activeFlag;
	private Long shopId;
	private String shop;
	private String shopType;
	
	private BigDecimal spending;
	private BigDecimal point;
	
	private Long programId;
	private Long locationId;
	private String location;
	
	//for search from criteria
	private Long shopTypeId;
	
	private Date createdDate;
	private String createdBy;
	
	private Date updatedDate;
	private String updatedBy;
	private Integer buId;
}
