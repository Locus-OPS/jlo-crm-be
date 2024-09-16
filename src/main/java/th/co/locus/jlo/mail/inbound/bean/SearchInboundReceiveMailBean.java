package th.co.locus.jlo.mail.inbound.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SearchInboundReceiveMailBean extends BaseModelBean implements Serializable {
	
	private static final long serialVersionUID = 5439212822221212607L;
	private String formEmail;
	private String toEmail;
	private String subjectEmail;
	private String startDate;
	private String endDate;	
	private String plainContent;

}
