package th.co.locus.jlo.business.landingpage.bean;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

@Data 
public class LandingPageModelBean  implements Serializable {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 5762683304077315758L;
	private Long id;
	private String questionnaireType;
	private String formName;
	private String sectionHeaderText;
	private String statusCd;
		
	
}
