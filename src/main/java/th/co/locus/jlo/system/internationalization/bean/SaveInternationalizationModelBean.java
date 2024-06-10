package th.co.locus.jlo.system.internationalization.bean;

import java.util.List;

import lombok.Data;

@Data
public class SaveInternationalizationModelBean {

	private String previousMsgCode;
	private String msgCode;
	private List<InternationalizationModelBean> translateList;
	
}
