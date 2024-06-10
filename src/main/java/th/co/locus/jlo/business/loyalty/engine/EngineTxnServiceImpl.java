package th.co.locus.jlo.business.loyalty.engine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionCancelModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionEnrollModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionReceiptModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionRedeemModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.engine.client.EngineClient;
import th.co.locus.jlo.engine.client.response.ProcessTransactionResponse;
import th.co.locus.jlo.engine.client.response.ResponseMessage;

@Service
@Slf4j
public class EngineTxnServiceImpl extends BaseService implements EngineTxnService  {
	


	@Autowired
	private EngineClient engineClient;
	
	
	@Override
	@Transactional
	public ServiceResult<TransactionModelBean> enrollMember(TransactionEnrollModelBean param){
		
		
		//return to front end
		String resultCode = "";
		String resultDesc = "";
		
		//validate error
		Map<String,String> validateErr = new HashMap<String,String>();
		//cancelValidate.put("MEMBER_ALREADY_ENROLLED"        , "This member already enroll");
		validateErr.put("INSERT_TXN_ENROLL_FAIL" , "Insert txn record for enroll fail");
		validateErr.put("REQUIRE_MEMBER_ID"      , "Require parameter memberId as a parameter");
		validateErr.put("PRODUCT_NOT_FOUND"      , "Not found PRODUCT for this loyalty program");
		validateErr.put("FOUND_MULTIPLE_PRODUCT" , "Found multiple product setting for this loyalty program.Please specific PRODUCT_CODE");

		//step0 check mandatory field
		StringUtil.nullifyObject(param);
		if (param.getMemberId() == null) {
			resultDesc = validateErr.get("REQUIRE_MEMBER_ID");
		}
		
		if (!StringUtil.isNullOrEmpty(resultDesc)) {
			return fail(resultCode, resultDesc);
		}
		
		//step1 check enroll product configuration
		/*
		 * if parameter productCode not set 
		 * 		-- the result will find all product that match to the enroll program  
		 *         ( error when found product more than 1 product | if no configure product it not error but not insert product record to txn )
		 * if parameter productCode is set 
		 *      -- the result will find by product code ( error when not found )
		 */
		TransactionModelBean txn = new TransactionModelBean();
		
		//create a set of parameter
		Map<String , Object> p = new HashMap<String, Object>();
		p.put("memberId", param.getMemberId());
		p.put("productCode", param.getProductCode());
		p.put("loyProductType","ENROLL_MEMBER"); 
		
		//find loyalty product product ( for set to transaction ( if found ))
		List<ProductModelBean> productList =  commonDao.selectList("engine.findProductByProductCode", p );
		if( productList.size() > 1 ) {
			//found more than product
			resultDesc = validateErr.get("FOUND_MULTIPLE_PRODUCT");
			
		}else if( productList.size() == 1 ){
			//found 1 product
			//set to txnBean
			txn.setProductId(productList.get(0).getProductId().intValue());
			txn.setProductCode(productList.get(0).getProductCode());
			txn.setProductType(productList.get(0).getProductType());
			txn.setProductName(productList.get(0).getProduct());
			txn.setLoyProductTypeId(productList.get(0).getProductTypeId());
			txn.setLoyProductType(productList.get(0).getProductType());
			 
		}
		//end step1
		
		
		//step2 set parameter to txn
		txn.setMemberId( Long.valueOf(param.getMemberId()) );
		txn.setChannel( param.getChannel() );
		
		
		
		//default set txn for enrollment
		txn.setTxnType( "EARN");
		txn.setTxnSubType("ENROLLMENT");
		txn.setTxnStatus("QUEUED");
		txn.setChannel( param.getChannel());
		
		
//		txn.setReceiptId( param.getReceiptId());
//		txn.setReceiptDate( param.getReceiptDate() );
//		txn.setAmount(  BigDecimal.valueOf( Double.valueOf(param.getSpending())) );
//		txn.setSpending(  BigDecimal.valueOf( Double.valueOf(param.getSpending())) );
		
		txn.setCreatedBy( CommonUtil.getUserId() );
		txn.setUpdatedBy( CommonUtil.getUserId() );
			
		//step3 insert to loy_txn table
		long txnId  = commonDao.insert("engine.insertEnrollTransaction", txn );
		if( txnId > 0 ) {
		 	return success( commonDao.selectOne("engine.getTransactionById", txn ));
		}
		
		return fail(resultCode,resultDesc);
	    
	}
	
	
	@Override
	@Transactional
	public ServiceResult<TransactionModelBean> earnReceipt(TransactionReceiptModelBean param){

		// return to front end
		String resultCode = "";
		String resultDesc = "";

		// validate error
		Map<String, String> validateErr = new HashMap<String, String>();

		validateErr.put("REQUIRE_RECEIPT_ID", "Require receipt id as a parameter");
		validateErr.put("REQUIRE_RECEIPT_DATE", "Require receipt date as a parameter");
		validateErr.put("REQUIRE_MEMBER_ID", "Require memberId as a parameter");
		validateErr.put("REQUIRE_SHOP_ID", "Require shopId as a parameter");
		validateErr.put("REQUIRE_SPENDING", "Require spending as a parameter");

		validateErr.put("DUPLICATE_RECEIPT", "This receipt already exist");
		validateErr.put("FOUND_MULTIPLE_PRODUCT" , "Found multiple product setting for this loyalty program.Please specific PRODUCT_CODE");

		//step0 check mandatory field
		StringUtil.nullifyObject(param);
		if (param.getReceiptId() == null) {
			resultDesc = validateErr.get("REQUIRE_RECEIPT_ID");
		}
		if( param.getReceiptDate() == null) {
			resultDesc = validateErr.get("REQUIRE_RECEIPT_DATE");
		}
		if (param.getMemberId() == null) {
			resultDesc = validateErr.get("REQUIRE_MEMBER_ID");
		}
		if (param.getShopId() == null) {
			resultDesc = validateErr.get("REQUIRE_SHOP_ID");
		}
		if (param.getSpending() == null) {
			resultDesc = validateErr.get("REQUIRE_SPENDING");
		}
		
		if (!StringUtil.isNullOrEmpty(resultDesc)) {
			return fail(resultCode, resultDesc);
		}

		//step1 check duplicate receipt
		List<TransactionReceiptModelBean> result = commonDao.selectList("engine.checkExistingTXNReceipt", param );
		log.info("result : "+result.size());
		if( result.size() == 0 ){ 
			//step2 check earn product configuration
			/*
			 * if parameter productCode not set 
			 * 		-- the result will find all product that match to the enroll program  
			 *         ( error when found product more than 1 product | if no configure product it not error but not insert product record to txn )
			 * if parameter productCode is set 
			 *      -- the result will find by product code ( error when not found )
			 */
			
			TransactionModelBean txn = new TransactionModelBean();
			
			//create a parameter
			Map<String , String> p = new HashMap<String, String>();
			p.put("memberId", param.getMemberId());
			p.put("productCode", param.getProductCode());
			p.put("loyProductType","POS_SALE");  
			
			
			//find loyalty product product ( for set to transaction ( if found ))
			List<ProductModelBean> productList =  commonDao.selectList("engine.findProductByProductCode", p);
			if( productList.size() > 1 ) {
			
				//found more than product
				resultDesc = validateErr.get("FOUND_MULTIPLE_PRODUCT");
				
			}else if(productList.size() == 1){
			
				//found 1 product
				//set to txnBean
				txn.setProductId(productList.get(0).getProductId().intValue());
				txn.setProductCode(productList.get(0).getProductCode());
				txn.setProductType(productList.get(0).getProductType());
				txn.setLoyProductTypeId(productList.get(0).getProductTypeId());
				
				
			}
			//end step2 
			
			//step3 find shop by id
			ShopModelBean shop = new ShopModelBean();
			shop.setShopId(Long.valueOf(param.getShopId()));
			shop = commonDao.selectOne("shop.findShopById", shop );
			if( shop != null ) {
				log.info("found shop");
				txn.setStoreShopId(   String.valueOf(shop.getShopId())     );
				txn.setStoreShopName( String.valueOf(shop.getShopName())   );
				
				txn.setStoreShopTypeId( String.valueOf(shop.getShopTypeId()) );
				txn.setStoreShopType( String.valueOf(shop.getShopTypeName()) );
			}
			
			
			//set parameter to txn
			txn.setMemberId( Long.valueOf(param.getMemberId()) );
			txn.setChannel( param.getChannel() );
			
			txn.setReceiptId( param.getReceiptId());
			txn.setReceiptNo( param.getReceiptNo());
			txn.setStoreShopId(param.getShopId());
			txn.setReceiptDate( param.getReceiptDate() );
			txn.setAmount(  BigDecimal.valueOf( Double.valueOf(param.getSpending())) );
			txn.setSpending(  BigDecimal.valueOf( Double.valueOf(param.getSpending())) );
			txn.setCreatedBy( CommonUtil.getUserId() );
			txn.setUpdatedBy( CommonUtil.getUserId() );
			
			//default set txn for earn receipt
			txn.setTxnStatus("QUEUED");
			txn.setTxnSubType("PRODUCT");
			txn.setTxnType("EARN");
			
			//step3 insert to loy_txn table
			long txnId  = commonDao.insert("engine.insertReceiptTXN", txn);
			if( txnId > 0 ) {
				log.info("EARN Receiipt insert TXN success with id :"+txn.getTxnId());
				
				//step3.1 insert to loy_receipt table
				//long receiptId  = commonDao.update("receipt.insertReceipt", txn);
				
				//step3.2 insert to loy_receipt_item table (option)
				
				
				//query new data
				return success( commonDao.selectOne("engine.getTransactionById", txn ));
			}
			
		}else{
			//found duplicate receipt
			resultDesc = validateErr.get("DUPLICATE_RECEIPT");
		}
		
	
		 log.info("EARN Receipt Error : "+resultDesc);
		 resultDesc = "EARN Receipt Error : "+resultDesc;
		 return fail( resultCode ,  resultDesc );
	}
	
	
	
	@Override
	public ServiceResult<TransactionModelBean> cancelTransaction(TransactionCancelModelBean param){
		
		
		//return to front end
		String resultCode = "";
		String resultDesc = "";
		
		//validate error
		Map<String,String> validateErr = new HashMap<String,String>();
		validateErr.put("TXN_NOT_FOUND"           , "No transaction found for cancel");
		validateErr.put("STATUS_CAN_NOT_CANCEL"   , "Can not cancel transaction in status");
		validateErr.put("STATUS_ALREADY_CANCELLED", "This transaction already cancelled, Can not cancel again");
		validateErr.put("INSERT_TXN_CANCEL_FAIL"  , "Insert txn record for cancel fail");
		validateErr.put("CAll_ENGINE_FAIL"        , "create transaction success but call engine fail please try to call engine again with transaction id : ");
		
		validateErr.put("REQUIRED_TXN_ID"          , "Require parameter transactionId as a parameter");
		validateErr.put("REQUIRED_CREATED_BY"      , "Require parameter createdBy as a parameter");
		
		//step0 check mandatory field
		StringUtil.nullifyObject(param);
		if (param.getTxnId() == null) {
			resultDesc = validateErr.get("REQUIRED_TXN_ID");
		}
		
		if (!StringUtil.isNullOrEmpty(resultDesc)) {
			return fail(resultCode, resultDesc);
		}
		
		 //step1 check current TXN_ID is found in LOY_TXN
		TransactionModelBean tmp = new TransactionModelBean();
		tmp = commonDao.selectOne("engine.getTransactionById", param);
		if (tmp != null) {

			// step2 check status is ready for cancel
			switch (tmp.getTxnStatusId().toUpperCase()) {
			 	case "PROCESSED" :  //only transaction in status PROCESSED can do a cancel transaction
			 						//create a transaction for cancel by insert new transaction for tell engine to create current txn
			 						tmp.setCreatedBy(CommonUtil.getUserId());
			 						int result = commonDao.update("engine.insertCancelTxn",tmp);

			 						if( result > 0 ){
			 							 
			 							  
			 							 long cancelTxnId = Long.valueOf( tmp.getTxnId() );
			 							 log.info("success create new cancel txn id ["+cancelTxnId+"] prepare to call engine...");
			 							 //get txnId from insert then call the engine
			 							 ResponseMessage<ProcessTransactionResponse> engine = engineClient.processTransaction(cancelTxnId);
			 							 if (engine != null && engine.isSuccess()){
			 								//if call engine success query txnId that call for cancel
			 								 log.info("success to call engine with txn id ["+cancelTxnId+"]");
			 								return success(commonDao.selectOne("engine.getTransactionById", param ));
			 							}else {
			 								//if call engine fail 
			 								resultDesc = validateErr.get("CAll_ENGINE_FAIL")+" "+tmp.getTxnId();
			 							}
			 							 
			 							
			 						}else{
			 							//insert new txn for cancel fail
			 							resultDesc = validateErr.get("INSERT_TXN_CANCEL_FAIL");
			 						}
			 						
			 						break;
			 						
			 	case "CANCELLED" :  //status is already cancelled can not cancel again
			 						resultDesc = validateErr.get("STATUS_ALREADY_CANCELLED");
 									break;
 									
 				default          :	//status is not valid for cancel 
 						 			resultDesc = validateErr.get("STATUS_CAN_NOT_CANCEL")+" "+tmp.getTxnStatus();
			 
			 }
			 
			 
			 
		 } else {
			 //error not found transaction for cancel
			 resultDesc = validateErr.get("TXN_NOT_FOUND");
			
		 }
		 
		 log.info("CANCEL Transaction Error : "+resultDesc);
		 resultDesc = "CANCEL Transaction Error : "+resultDesc;
		 
		 return fail( resultCode ,  resultDesc ); 
		 
	 }
	
	
	
	@Override
	public ServiceResult<TransactionModelBean> redeem(TransactionRedeemModelBean param){
		
		//return to front end
		String resultCode = "";
		String resultDesc = "";

		// validate error
		Map<String, String> validateErr = new HashMap<>();
		validateErr.put("REQUIRE_MEMBER_ID", "Require parameter memberId as a parameter");
		validateErr.put("REQUIRE_REDEEM_METHOD", "Require parameter redeem method (POINT,CASH, or POINT + CASH) as a parameter");
		validateErr.put("REQUIRE_REDEEM_POINT", "Require parameter redeem point as a parameter");
		validateErr.put("REQUIRE_REDEEM_CASH", "Require parameter redeem cash as a parameter");
		validateErr.put("REQUIRE_REDEEM_POINTCASH", "Require parameter redeem point and redeem cash as a parameter");
		validateErr.put("REQUIRE_PRODUCT_CODE", "Require paraemter productCode as a parameter");
		validateErr.put("REQUIRE_QUANTITY", "Require parameter quantity as a parameter");

		validateErr.put("NOT_FOUND_PRODUCT", "Not found product or product is in active.");

		validateErr.put("NOT_FOUND_MEM_POINT_BALANCE", "Not found member point balance.Please contact administrator");
		validateErr.put("NOT_FOUND_REDEEM_HEADER", "Please insert data to LOY_REDEMTION");
		validateErr.put("NOT_FOUND_REDEEM_ITEM", "Please insert data to LOY_REDEMTION_ITEM");
		validateErr.put("REQUIRE_REQUEST_POINT", "Not found PRODUCT for this loyalty program");
		validateErr.put("FOUND_MULTIPLE_PRODUCT", "Found multiple product setting for this loyalty program.Please specific PRODUCT_CODE");

		validateErr.put("CAll_ENGINE_FAIL", "create transaction success but call engine fail please try to call engine again with transaction id : ");

		//step0 check mandatory field
		StringUtil.nullifyObject(param);
		if (param.getMemberId() == null) {
			resultDesc = validateErr.get("REQUIRE_MEMBER_ID");
		}

		// check product code ( redeem product_id )
		if (param.getProductCode() == null) {
			resultDesc = validateErr.get("REQUIRE_PRODUCT_CODE");
		}

		// check quantity
		if (param.getProductCode() == null) {
			resultDesc = validateErr.get("REQUIRE_QUANTITY");
		}

		// check redeem method
		if (param.getRedeemMethod() == null) {
			resultDesc = validateErr.get("REQUIRE_REDEEM_METHOD");
		} else {
			// if redeem method is P ( POINT )
			if ("P".equalsIgnoreCase(param.getRedeemMethod())) {
				// check field request point
				if (param.getRequestPoint() == null) {
					resultDesc = validateErr.get("REQUIRE_REDEEM_POINT");
				}
			} else if ("C".equalsIgnoreCase(param.getRedeemMethod())) { // if redeem method is C (CASH)
				// check field request cash
				if (param.getRequestCash() == null) {
					resultDesc = validateErr.get("REQUIRE_REDEEM_CASH");
				}
			} else if ("PC".equalsIgnoreCase(param.getRedeemMethod())) { // if redeem method IS PC ( POINT + CASH )
				// check field request point and request cash
				if (param.getRequestPoint() == null || param.getRequestCash() == null) {
					resultDesc = validateErr.get("REQUIRE_REDEEM_POINTCASH");
				}
			}
			
		}

		// return fail if require field is empty
		if (!StringUtil.isNullOrEmpty(resultDesc)) {
			return fail(resultCode, resultDesc);
		}

		TransactionModelBean txn = new TransactionModelBean();
		
		// step1 find product id ( loy_product where type = 'reward' )
		// create a set of parameter
		Map<String, Object> p = new HashMap<>();
		p.put("productCode", param.getProductCode());
		p.put("loyProductType", "REWARD");
		p.put("memberId", param.getMemberId());

		// find loyalty product product ( for set to transaction ( if found ))
		List<ProductModelBean> productList = commonDao.selectList("engine.findProductByProductCode", p);
		if (productList.size() > 1) {
			// found more than one product
			resultDesc = validateErr.get("FOUND_MULTIPLE_PRODUCT");

		} else if (productList.size() == 1) {
			// found 1 product
			// set to txnBean
			txn.setProductId(productList.get(0).getProductId().intValue());
			txn.setProductCode(productList.get(0).getProductCode());
			txn.setProductType(productList.get(0).getProductType());
			txn.setProductName(productList.get(0).getProduct());
			txn.setLoyProductTypeId(productList.get(0).getProductTypeId());
			txn.setRewardTypeId(String.valueOf("")); // productList.get(0).getRewardTypeId()

		} else {
			// not found product
			resultDesc = validateErr.get("NOT_FOUND_PRODUCT");

		}

		// return fail if require field is empty
		if (!StringUtil.isNullOrEmpty(resultDesc)) {
			return fail(resultCode, resultDesc);
		}

		//step2 find shop by id
//		ShopModelBean shop = new ShopModelBean();
//		shop.setShopId(Long.valueOf(param.getShopId()));
//		shop = commonDao.selectOne("shop.findShopById", shop );
//		if( shop != null ) {
//			log.info("found shop");
//			txn.setStoreShopId(   String.valueOf(shop.getShopId())     );
//			txn.setStoreShopName( String.valueOf(shop.getShopName())   );
//			
//			txn.setStoreShopTypeId( String.valueOf(shop.getShopTypeId()) );
//			txn.setStoreShopType( String.valueOf(shop.getShopTypeName()) );
//		}

		
		//step2 check redeem item is existing ( validate status , expiration date , redeem method ) 
		/*
		TransactionModelBean tmp = new TransactionModelBean();
		tmp = commonDao.selectOne("engine.getTransactionById", param);
		if (tmp != null) {
			//select redeem
		}
		*/
		
		//step3 check total redeem point
		
		//step4 if all pass create transaction first
		//then call engine for duduct point
		
		//step5 insert loy_redeen ( header )
		//step6 insert loy_redeem_item ( item )
	
		
//	    txn.setSpending( BigDecimal.valueOf( Double.valueOf( param.getSpending() )));
//	    txn.setReceiptId( param.getReceiptId() );
//	    txn.setReceiptNo( param.getReceiptNo() );
//	    txn.setReceiptDate( param.getReceiptDate() );

		txn.setMemberId(param.getMemberId());
		txn.setChannel(param.getChannel());
		txn.setRequestCash(param.getRequestCash());
		txn.setRequestPoint(Integer.valueOf(param.getRequestPoint()));
		txn.setQuantity(Integer.valueOf(param.getQuantity()));
		txn.setRedeemMethod(param.getRedeemMethod());
		txn.setCreatedBy(CommonUtil.getUserId());
		txn.setUpdatedBy(CommonUtil.getUserId());

		//default set txn for redeem
		txn.setTxnType("BURN");
		txn.setTxnSubType("PRODUCT");
		txn.setTxnStatus("QUEUED");

		// insert redeem txn
		int result = commonDao.insert("engine.insertRedeemTxn", txn);
		if (result > 0) {

			long redeemTxnId = Long.valueOf(txn.getTxnId());
			log.info("success create redemption txn id [" + redeemTxnId + "] prepare to call engine...");
			// get txnId from insert then call the engine
			ResponseMessage<ProcessTransactionResponse> engine = engineClient.processTransaction(redeemTxnId);
			if (engine != null && engine.isSuccess()) {
				// if call engine success query txnId that call for cancel
				log.info("success to call engine with txn id [" + redeemTxnId + "]");
				return success(commonDao.selectOne("engine.getTransactionById", txn));
			} else {
				// if call engine fail
				resultDesc = validateErr.get("CAll_ENGINE_FAIL") + " " + txn.getTxnId();
			}

		} else {
			// insert new txn for cancel fail
			resultDesc = validateErr.get("INSERT_TXN_CANCEL_FAIL");
		}

		if (!StringUtil.isNullOrEmpty(resultDesc)) {
			return fail(resultCode, resultDesc);
		}

		return new ServiceResult<>(true);
	}

}
