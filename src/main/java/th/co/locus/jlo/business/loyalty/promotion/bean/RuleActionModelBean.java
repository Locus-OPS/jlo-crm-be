package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class RuleActionModelBean {
	private Long actionId;
	private Long ruleId;
	private Long promotionId;
	
	private String actionType;
	private String actionDetail;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer pointType;
	private String pointOperator;
	private String pointValue;
	private String pointExpireUnit;
	private Integer pointExpirePeriod;

	private String updObj;
	private String updAttr;
	private String updOperator;
	
	private String withObj;
	private String withAttr;
	private String withOperator;
	private String withValue;
	private String exp;
	
	private String actionQuery;
	private String useTxnPointTypeFlag;
	
	private LocalDateTime pointExpireDate;

	// custom
	private String updGroup;
	private String updName;
	private String updObject;
	private String updField;
	private String updDataTypeId;
	
	private String withGroup;
	private String withName;
	private String withObject;
	private String withField;
	private String withDataTypeId;
	private String pointTypeId;
	private String pointTypeName;
	
	private LocalDateTime createdDate;
	private String createdBy;
	
	private LocalDateTime updatedDate;
	private String updatedBy;
}
