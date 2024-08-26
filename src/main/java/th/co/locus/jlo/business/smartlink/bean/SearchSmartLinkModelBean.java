package th.co.locus.jlo.business.smartlink.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

 
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchSmartLinkModelBean extends SortingModelBean {

	private Long systemCode;

	private String idNumber;

	private String hashKey;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date consentDate;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date expireDate;
}
