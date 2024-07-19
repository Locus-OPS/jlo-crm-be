package th.co.locus.jlo.system.email.emaillog.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchEmailLogBean extends SortingModelBean {
	private String statusCode;
}
