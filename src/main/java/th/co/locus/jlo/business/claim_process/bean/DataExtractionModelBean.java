package th.co.locus.jlo.business.claim_process.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataExtractionModelBean extends BaseModelBean {

	private Integer id;
	private Integer attId;
	private String codeId;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date itemDate;
	private String itemName;
	private Double quantity;
	private Double unitCost;
	private Double totalCost;
	private Double discount;
	private Double amount;
	private String genericName;
	private String strength;
	private String dosageNo;
	private String dosageUnit;
	private String unitName;
	private String itemCategory;

}
