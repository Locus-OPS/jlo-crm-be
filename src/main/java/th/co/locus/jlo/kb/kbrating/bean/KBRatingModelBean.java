package th.co.locus.jlo.kb.kbrating.bean;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class KBRatingModelBean extends BaseModelBean {
	private Long id;
	private Long userId;
	private Long contentId;
	private BigDecimal rating;
	private BigDecimal avgRating;
	private Long totalReviewer;
}
