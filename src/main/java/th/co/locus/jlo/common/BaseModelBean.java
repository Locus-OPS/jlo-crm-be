package th.co.locus.jlo.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseModelBean {

	private Integer buId;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createdDate;
	private String createdBy;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date updatedDate;
	private String updatedBy;
	
}
