package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchCardCriteriaModelBean extends SortingModelBean {
	
    private Long programId;
    private String firstName;
    private String lastName;
	private String cardNumber;
	private Long memberId;
	
	
}
