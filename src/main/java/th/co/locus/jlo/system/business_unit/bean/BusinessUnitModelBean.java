package th.co.locus.jlo.system.business_unit.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class BusinessUnitModelBean extends BaseModelBean {

	private Integer id;
	private String buName;
	private String activeYn;
}
