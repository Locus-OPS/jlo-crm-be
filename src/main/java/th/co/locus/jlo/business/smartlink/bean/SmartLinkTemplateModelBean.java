package th.co.locus.jlo.business.smartlink.bean;

import lombok.Data;

 
@Data
public class SmartLinkTemplateModelBean {
	private Long landingId;
	private Long systemId;
	private String language;
	private String title;
	private String subtitle;
	private String footer;

	//private List<ConsentMasterModelBean> consentList;
}
