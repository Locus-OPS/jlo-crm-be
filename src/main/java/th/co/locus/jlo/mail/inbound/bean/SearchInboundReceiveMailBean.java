package th.co.locus.jlo.mail.inbound.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchInboundReceiveMailBean extends SortingModelBean {

	private String formEmail;
	private String toEmail;
	private String subjectEmail;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date startDate;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date endDate;

	private String plainContent;
	private String statusCd;

}
