package th.co.locus.jlo.util.selector.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SelectorModelBean {

	public SelectorModelBean(){}

	private String codeId;
	private String codeType;
	private String codeName;
	private String parentType;
	private String parentId;
	private String lang;
	private Integer seq;
	private String etc1;
	private String etc2;
	private String etc3;
	private String etc4;
	private String etc5;
	private Integer buId;
	private String activeFlag;
}
