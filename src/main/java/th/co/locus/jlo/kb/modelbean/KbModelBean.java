package th.co.locus.jlo.kb.modelbean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class KbModelBean extends BaseModelBean {
	
	private Long contentId;
	private Long catId;
	private String catName;
	private String type;
	private String subType;
	private String title;
	private String contentStatus;
	private String display;
	private Integer seq;
	private String sendDocFlag;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	@JsonFormat(pattern = "HH:mm:ss")
	private Date startTime;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date startDateTime;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	@JsonFormat(pattern = "HH:mm:ss")
	private Date endTime;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date endDateTime;
	
}
