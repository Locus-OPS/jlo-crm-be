package th.co.locus.jlo.exception.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExceptionModelBean extends BaseModelBean {
	private String serviceName;
	private String errorCode;
	private String errorMsg;
	private String errorDescp;
}
