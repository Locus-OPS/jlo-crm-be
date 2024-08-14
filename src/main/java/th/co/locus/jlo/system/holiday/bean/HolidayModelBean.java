package th.co.locus.jlo.system.holiday.bean;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class HolidayModelBean extends BaseModelBean {
	public BigInteger id;
	public BigInteger year;
	public String typeCd;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	public DateTime holidayDate;
	public String holidayName;
	public String remark;
}
