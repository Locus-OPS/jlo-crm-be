package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class RuleCriteriaModelBean {
	
	private Long criteriaId;
	private Long ruleId;
	private Long promotionId;
	
	private String description;
	private String exp;
	private String srcObj;
	private String srcAttr;
	private String srcCondition;
	private String compareToOv;
	private String dscObj;
	private String dscAttr;
	private String dscOperator;
	
	private String dscValue;
	// for internal purpose - setRuleCriteriaExpression
	private List idList; 
	
	private String dscValueExp;
	private String lov;
	
	// custom
	private String dscObjValue;
	private String srcObjectName;
	private String srcFieldName;
	private String srcDataType;
	private String dscObjectName;
	private String dscFieldName;
	private String dscDataType;
	
	private LocalDateTime createdDate;
	private String createdBy;
	
	private LocalDateTime updatedDate;
	private String updatedBy;
}
