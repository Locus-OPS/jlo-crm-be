package th.co.locus.jlo.kb.modelbean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class KbKeywordSearchModelBean extends BaseModelBean  {
	private Long contentId;
	private String title;
	private String description;
	private String url;
	private String keyword;
}
