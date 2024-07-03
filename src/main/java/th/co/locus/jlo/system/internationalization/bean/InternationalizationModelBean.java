package th.co.locus.jlo.system.internationalization.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class InternationalizationModelBean extends BaseModelBean {

    private String msgCode;

    private String msgValue;

    private String langCd;
}
