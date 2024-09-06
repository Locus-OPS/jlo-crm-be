/**
 * 
 */
package th.co.locus.jlo.util;

import th.co.locus.jlo.common.util.ExcelTemplate;

/**
 * 
 */
public enum JloExcelTemplate implements ExcelTemplate {

	REPORT_JLO_CRM01("report_jlo_crm01.xlsx", 3),   
	REPORT_JLO_CRM02("report_jlo_crm02.xlsx", 2),
	REPORT_JLO_QTN_Q_SUMMARY("report_jlo_qtn_q_summary.xlsx", 3),
	REPORT_JLO_QTN_Q_SUMMARY_LIST("report_jlo_qtn_q_summary_list.xlsx", 2),
	;

	private final String name;
	private final int startRow;

	JloExcelTemplate(String name, int startRow) {
		this.name = name;
		this.startRow = startRow;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getStartRow() {
		return startRow;
	}

}
