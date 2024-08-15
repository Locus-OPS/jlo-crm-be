package th.co.locus.jlo.system.holiday.bean;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class HolidayModelBean extends BaseModelBean {
	public BigInteger id;
	public BigInteger year;
	public String typeCd;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	public Date holidayDate;
	public String holidayName;
	public String remark;
}
