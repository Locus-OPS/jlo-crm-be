package th.co.locus.jlo.business.loyalty.promotion.bean;

import lombok.Data;

@Data
public class TableMappingBean {
	private String sourceTable;
	private String sourceKey;
	private String destinationTable;
	private String destinationKey;
	private String addition;
	
	public TableMappingBean(String sourceTable, String sourceKey, String destinationTable, String destinationKey) {
		this.sourceTable = sourceTable;
		this.sourceKey = sourceKey;
		this.destinationTable = destinationTable;
		this.destinationKey = destinationKey;
	}
	
	public TableMappingBean(String sourceTable, String sourceKey, String destinationTable, String destinationKey, String addition) {
		this.sourceTable = sourceTable;
		this.sourceKey = sourceKey;
		this.destinationTable = destinationTable;
		this.destinationKey = destinationKey;
		this.addition = addition;
	}
}
