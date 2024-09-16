package th.co.locus.jlo.kb.kbfavorite.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class KBFavoriteModelBean extends BaseModelBean{
	private Long id;
	private Long userId;
	private Long contentId;
}
