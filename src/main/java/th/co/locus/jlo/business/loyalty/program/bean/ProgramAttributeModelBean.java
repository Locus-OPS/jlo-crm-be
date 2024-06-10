package th.co.locus.jlo.business.loyalty.program.bean;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProgramAttributeModelBean extends SortingModelBean {

	private Long attrId;
	private String attrName;
	private Long attrGroupId;
	private String attrGroupName;
	private Long programId;
	private String attrActiveYn;
	private String objectName;
	private String fieldName;
	private String readOnlyYn;
	private String dataType;
	private String dataTypeName;
	private String attrPickList;
	private String method;
	private String methodName;
	private String remark;
	
	private String defaultValue;
	private String promotionAttrIdNotEqual;

	private Integer buId;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createdDate;
	private String createdBy;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date updatedDate;
	private String updatedBy;
}
