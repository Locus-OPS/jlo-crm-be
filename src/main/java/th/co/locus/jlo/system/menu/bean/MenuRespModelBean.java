package th.co.locus.jlo.system.menu.bean;

import lombok.Data;

@Data
public class MenuRespModelBean {

	private Long id;
	private String name;
	private String type;
	private String icon;
	private String link;
	private Long parentMenuId;
	private String respFlag;
	private String apiPath;
	private String use;
	
}
