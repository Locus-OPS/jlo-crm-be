package th.co.locus.jlo.business.claim_process.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClaimProcessCriteriaModelBean extends SortingModelBean {

	private int attId;
	private String itemName;
	private String itemCategory;
	
}
