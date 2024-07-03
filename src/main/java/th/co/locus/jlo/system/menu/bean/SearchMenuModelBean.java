package th.co.locus.jlo.system.menu.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMenuModelBean extends SortingModelBean {

    private String name;

    private String parentMenuId;
}
