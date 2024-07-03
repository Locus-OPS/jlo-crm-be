package th.co.locus.jlo.kb.modelbean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateKbFileSequenceModelBean extends BaseModelBean {

	private Long contentId;
	private Integer moveFlag;
}
