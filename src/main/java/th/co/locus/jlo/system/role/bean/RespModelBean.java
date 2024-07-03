package th.co.locus.jlo.system.role.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class RespModelBean extends BaseModelBean {

	private String respCode;
	private String respName;
	private String respFlag;
	private String type;
	private Integer menuId;
	private Integer parentMenuId;
	
}
