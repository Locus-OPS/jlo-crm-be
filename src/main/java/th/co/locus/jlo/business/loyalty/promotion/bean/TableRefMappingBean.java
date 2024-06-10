package th.co.locus.jlo.business.loyalty.promotion.bean;

import lombok.Data;

@Data
public class TableRefMappingBean {
	private String sourceTable;
	private String refTable;
	
	public TableRefMappingBean(String sourceTable, String refTable) {
		this.sourceTable = sourceTable;
		this.refTable = refTable;
	}
}
