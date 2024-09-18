package th.co.locus.jlo.mail.inbound.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class InboundReceiveMailBean extends BaseModelBean implements Serializable {
	
	private static final long serialVersionUID = 5439212822221212607L;
	private Long id;
	private String formEmail;
	private String toEmail;
	private String toCcEmail;
	private String toBccEmail;
	private String replyToEmail;	
	private String subjectEmail;
	private String plainContent;
	private String htmlContent;
	private String statusCd;
	private String statusName;
	private String description;

}
