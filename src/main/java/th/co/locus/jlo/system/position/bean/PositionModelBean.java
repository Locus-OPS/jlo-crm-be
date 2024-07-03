package th.co.locus.jlo.system.position.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class PositionModelBean extends BaseModelBean {
	
	private Long posId;
	private String posName;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentPosId;
	
	private String parentPosName;

}
