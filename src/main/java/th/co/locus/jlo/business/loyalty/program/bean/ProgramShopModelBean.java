package th.co.locus.jlo.business.loyalty.program.bean;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProgramShopModelBean extends SortingModelBean {
	
	private Long programShopExcludedId;
	private Long programShopIncludedId;
	
	private Long programId;
	
	private String locationId;
	private String location;
	private String shopId;
	private String shop;
	private String shopNo;
	private String shopTypeId;
	private String shopType;
	
	private LocalDateTime createdDate;
	private String createdBy;
	
	private LocalDateTime updatedDate;
	private String updatedBy;
	
	private List<String> lstShopId;
	private String isActive;
	private int isExistShop;
	private String locationCdAll;
	private String shopTypeIdAll;
	private Integer buId;
}
