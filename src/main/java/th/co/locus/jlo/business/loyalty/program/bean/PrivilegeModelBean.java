package th.co.locus.jlo.business.loyalty.program.bean;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class PrivilegeModelBean extends SortingModelBean {

	private Long serviceId;
	private Long programId;
	private Long tierId;

	private String serviceName;

	private String serviceType;
	private String serviceTypeName;

	private String unit;
	private String unitName;
	private Integer unitValue;

	private String location;
	private String locationName;

	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
}
