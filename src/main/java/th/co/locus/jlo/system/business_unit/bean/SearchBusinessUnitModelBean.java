package th.co.locus.jlo.system.business_unit.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class SearchBusinessUnitModelBean extends SortingModelBean {

    private String buName;
    private String activeYn;
}
