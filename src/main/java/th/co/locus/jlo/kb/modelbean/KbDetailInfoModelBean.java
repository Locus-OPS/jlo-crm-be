package th.co.locus.jlo.kb.modelbean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class KbDetailInfoModelBean extends BaseModelBean {
	
	private Long contentId;
	private String url;
	private String description;
}
