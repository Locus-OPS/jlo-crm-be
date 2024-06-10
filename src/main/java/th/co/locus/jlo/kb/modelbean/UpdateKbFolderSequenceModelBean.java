package th.co.locus.jlo.kb.modelbean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateKbFolderSequenceModelBean extends BaseModelBean {

	private Long catId;
	private Integer moveFlag;
}
