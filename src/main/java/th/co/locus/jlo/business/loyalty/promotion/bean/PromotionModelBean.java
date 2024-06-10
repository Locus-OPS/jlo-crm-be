package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramModelBean;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class PromotionModelBean extends SortingModelBean {
	
	private Long promotionId;
	private String promotion;
	private String promotionTypeId;
	private String promotionType;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date startDate;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date endDate;
	
	private Integer promotionReceiveTimeLimit;
	private String remark;
	private String activeFlag;
	private String baseFlag;
	private String useBasePointFlag;
	private String deleteFlag;
	
	private String productInclusionId;
	private Long productListId;
	
	private String memberInclusionId;
	private Long memberListId;
	
	private String shopInclusionId;
	private Long shopListId;
	
	private String capTypeId;
	
	private Integer minCap;
	private Integer maxCap;
	
	private Long promotionAttrId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long programId;
	private String program;
	
	private Date createdDate;
	private String createdBy;
	
	private Date updatedDate;
	private String updatedBy;
	
	private String promotionDuplicate;
	private Integer buId;
}
