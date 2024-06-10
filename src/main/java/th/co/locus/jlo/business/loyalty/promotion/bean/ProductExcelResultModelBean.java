package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
public class ProductExcelResultModelBean{
	private int success;
	private int error;
	private int totalRecord;
}
