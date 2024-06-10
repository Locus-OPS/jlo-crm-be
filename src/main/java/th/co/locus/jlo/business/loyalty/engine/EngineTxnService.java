package th.co.locus.jlo.business.loyalty.engine;


import th.co.locus.jlo.business.loyalty.engine.bean.TransactionCancelModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionEnrollModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionReceiptModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionRedeemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
import th.co.locus.jlo.common.ServiceResult;


/*
 *  Service for create transaction for calculate engine include validate when create a transaction
 *  1 manual point adjust
 *  2 earn receipt
 *  3 enroll member
 *  4 cancel transaction
 *  5 redeem transaction
 */

public interface EngineTxnService {
  
	
	ServiceResult<TransactionModelBean> earnReceipt(TransactionReceiptModelBean param);
	ServiceResult<TransactionModelBean> enrollMember(TransactionEnrollModelBean param);
    ServiceResult<TransactionModelBean> cancelTransaction(TransactionCancelModelBean param);
    
    ServiceResult<TransactionModelBean> redeem(TransactionRedeemModelBean param);
   
    
	
	
}
