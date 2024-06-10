package th.co.locus.jlo.business.loyalty.transaction;

import th.co.locus.jlo.business.loyalty.transaction.bean.BurnItemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.CardModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.EarnItemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.PromotionAttrModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchCardCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchTransactionCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface TransactionService {
	
	public ServiceResult<Page<TransactionModelBean>> getTransactionList(SearchTransactionCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<TransactionModelBean> getTransactionById(Long transactionId);
	public ServiceResult<Page<EarnItemModelBean>> getEarnItemList(SearchTransactionCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Page<BurnItemModelBean>> getBurnItemList(SearchTransactionCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Page<PromotionAttrModelBean>> getPromotionAttrList(SearchTransactionCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<TransactionModelBean> createTransaction(TransactionModelBean bean);
	public ServiceResult<TransactionModelBean> updateTransaction(TransactionModelBean bean);

	public ServiceResult<Page<CardModelBean>> getCardList(SearchCardCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Page<ProductModelBean>> getProductList(SearchProductCriteriaModelBean criteria, PageRequest pageRequest);

}
