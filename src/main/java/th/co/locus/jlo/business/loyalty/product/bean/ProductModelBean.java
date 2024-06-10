package th.co.locus.jlo.business.loyalty.product.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@Alias("loyaltyProductModelBean")
public class ProductModelBean extends BaseModelBean {

	private Long productId;
	private String productCode;
	private String product;
	private String productDetail;
	private String productFullDetail;
	private String productBarCode;
	private String productQrCode;
	private String productStatus;
	
	private String productTypeId;
	private String productType;
	private String productImgPath;
	private String productCategoryId;
	private String productCategory;
	private String productSubCategoryId;
	private String productSubCategory;

	private String productActiveFlag;
	private Double productPrice;
	private Long productPointUse;
	private String productBrand;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long programId;
	private String program;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long campaignId;
	private String campaign;
	
	private String loyProductTypeId;
	private String loyProductType;
	
	private String cc;
	private String gl;
	private String io;
	private String isGiftCardFlag;
	private Integer prizeMasterIsGiftcard;
	
	private Integer inventoryRedeemCount;
	private Integer inventoryBalance;
	private Integer quantity;
	
	private String fileOriginalName;
	private String filePhysicalName;
	private String bytesAvatar;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
}
