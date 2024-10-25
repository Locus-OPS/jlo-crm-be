package th.co.locus.jlo.business.customer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class CustomerAuditLogBean extends BaseModelBean {
	private Long id;
	private Long customerId;
	private String actionType;
	private String fieldName;
	private String oldValue;
	private String newValue;
	private String changedDetail;
	private String remark;
}
