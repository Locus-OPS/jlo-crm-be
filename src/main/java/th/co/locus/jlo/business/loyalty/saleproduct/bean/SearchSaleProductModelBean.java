package th.co.locus.jlo.business.loyalty.saleproduct.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

/**
 * 
 * @author Mr.BoonOom
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchSaleProductModelBean extends SortingModelBean {

    private String itemName;
    private String itemStatus;
    private Integer buId;
}
