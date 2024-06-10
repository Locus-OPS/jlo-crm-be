package th.co.locus.jlo.system.role.bean;

import java.util.List;

import lombok.Data;

@Data
public class UpdateRespModelBean {

	private String roleCode;
	private List<RespModelBean> respList;
	
}
