package th.co.locus.jlo.kb.modelbean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class KbTreeModelBean extends BaseModelBean {

	private String id;
	private String parentId;
	private String title;
	private boolean isFolder;
	private String contentType;
	private Integer seq;
	private String icon;
	
}
