package th.co.locus.jlo.workflow.bean;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessRuleModelBean extends BaseModelBean {
	private Long ruleId;
	private Long workflowId;
	private String conditionType;
	private BigDecimal conditionValue1;
	private BigDecimal conditionValue2;
	private Long priority;
	private String status;
}
