package th.co.locus.jlo.business.smartlink.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SmartLinkModelBean extends BaseModelBean {
	private Long id;

	private Long systemId;

	private Long headerId;

	private String hashKey;

	private String consentFlag;

	private Date consentDate;

	private String otpFlag;

	private Date otpDate;

	private String urlLink;

	private Date expireDate;

	//private List<SmartLinkTemplateModelBean> smartLinkTemplateList;
}
