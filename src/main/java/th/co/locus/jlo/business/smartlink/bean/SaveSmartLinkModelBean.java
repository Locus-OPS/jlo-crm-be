package th.co.locus.jlo.business.smartlink.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

/**
 * @author Thanaphat
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SaveSmartLinkModelBean extends BaseModelBean {
	private Long smartlinkId;

	private Long headerId;

	private String systemCode; 

	private String hashKey;

	private String consentFlag;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date consentDate;

	private String otpFlag;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date otpDate;

	private String urlLink;

	private Integer expireDay;

	private String sendBy;

	private String sendTo;

	private String langCode;
}
