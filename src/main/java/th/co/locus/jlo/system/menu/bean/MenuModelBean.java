package th.co.locus.jlo.system.menu.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuModelBean extends BaseModelBean {

    private Long id;

    private String name;

    private String type;

    private String icon;

    private String link;

    private String lang;

    private String activeFlag;

    private Integer seq;

    private String parentMenuId;
    
    private String apiPath;
}
