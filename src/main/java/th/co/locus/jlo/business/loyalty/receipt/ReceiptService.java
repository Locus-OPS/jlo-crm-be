package th.co.locus.jlo.business.loyalty.receipt;

import th.co.locus.jlo.business.loyalty.receipt.bean.ReceiptTXNModelBean;
import th.co.locus.jlo.common.ServiceResult;

public interface ReceiptService {
	
	public ServiceResult<ReceiptTXNModelBean> checkExistingTXNReceipt(ReceiptTXNModelBean bean);

	public ServiceResult<ReceiptTXNModelBean> saveReceipt( ReceiptTXNModelBean bean );
	
}
