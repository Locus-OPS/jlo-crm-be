/**
 * 
 */
package th.co.locus.jlo.business.cases.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;
;

/**
 * @author Mr.BoonOom
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchCaseModelBean extends SortingModelBean {
	private String divisionTypeCode;
	private String categoryTypeCode;
	private String caseNumber;
	private String type;
	private String subType;
    private Integer buId;
	private String status;
    private Long customerId;
	private String ownerId;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date openedDateStart;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date openedDateEnd;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date closedDateStart;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date closedDateEnd;
}

