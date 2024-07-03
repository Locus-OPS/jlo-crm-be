package th.co.locus.jlo.kb.modelbean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class KbKeywordModelBean extends BaseModelBean {

	private Long keyId;
	private Long contentId;
	private String keyword;
	
}
