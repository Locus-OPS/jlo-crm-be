package th.co.locus.jlo.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BaseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4942324153724408969L;
	
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String status;
	private String lang;
	
	private String editUrl;
	private String addUrl;
	private String deleteUrl;
	
	protected java.util.List<String> ownerGroup;
	
}
