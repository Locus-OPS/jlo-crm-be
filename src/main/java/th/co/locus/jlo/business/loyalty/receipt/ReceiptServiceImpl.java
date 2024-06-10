package th.co.locus.jlo.business.loyalty.receipt;

import org.springframework.stereotype.Service;
import th.co.locus.jlo.business.loyalty.receipt.bean.ReceiptTXNModelBean;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class ReceiptServiceImpl extends BaseService implements ReceiptService   {

	@Override
	public  ServiceResult<ReceiptTXNModelBean> checkExistingTXNReceipt(ReceiptTXNModelBean bean) { 
		return success(commonDao.selectOne("receipt.checkExistingTXNReceipt", bean));
	}	
	
	@Override
	public ServiceResult<ReceiptTXNModelBean> saveReceipt(ReceiptTXNModelBean bean ) {
		
		String countTxnReceipt = commonDao.selectOne("receipt.checkExistingTXNReceipt", bean );
		if( countTxnReceipt != null && countTxnReceipt != "" ) {
			long txnId    = commonDao.update("receipt.insertReceiptTXN", bean);
			//long recordId = commonDao.update("receipt.insertReceipt", receptBean );
			if( txnId > 0 ) {
				return success(commonDao.selectOne("receipt.getTXNReceipt", txnId ));
			}
		}
		return fail();
		
	}
	
	
}
