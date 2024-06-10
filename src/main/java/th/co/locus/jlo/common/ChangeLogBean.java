package th.co.locus.jlo.common;

import java.util.Date;

import lombok.Data;

@Data
public class ChangeLogBean {
	private String changedBy;
	private String changedDetail;
	private Date changedDate;
}
