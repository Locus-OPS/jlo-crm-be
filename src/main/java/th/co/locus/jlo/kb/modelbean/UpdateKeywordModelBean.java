package th.co.locus.jlo.kb.modelbean;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateKeywordModelBean {

	private Long contentId;
	private List<KbKeywordModelBean> keywordList;
	
}
