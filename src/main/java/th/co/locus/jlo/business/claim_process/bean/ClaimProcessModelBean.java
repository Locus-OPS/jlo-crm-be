package th.co.locus.jlo.business.claim_process.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClaimProcessModelBean extends BaseModelBean {

	private Integer id;
	private Integer attId;
	private String codeId;
	private String itemDate;
	private String itemName;
	private String quantity;
	private String unitCost;
	private String totalCost;
	private String discount;
	private String amount;
	private String genericName;
	private String strength;
	private String dosageNo;
	private String dosageUnit;
	private String unitName;
	private String itemCategory;

}
