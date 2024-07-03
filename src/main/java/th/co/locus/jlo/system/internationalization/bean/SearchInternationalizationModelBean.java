package th.co.locus.jlo.system.internationalization.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchInternationalizationModelBean extends SortingModelBean {

    private String msgCode;

    private String msgValue;
}
