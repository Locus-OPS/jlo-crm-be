package th.co.locus.jlo.business.loyalty.transaction;

import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.transaction.bean.BurnItemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.CardModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.EarnItemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.PromotionAttrModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchCardCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchTransactionCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class TransactionServiceImpl extends BaseService implements TransactionService {

	@Override
	public ServiceResult<Page<TransactionModelBean>> getTransactionList(SearchTransactionCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("transaction.getTransactionList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<TransactionModelBean> getTransactionById(Long txnId) {
		return success(commonDao.selectOne("transaction.findTransactionById", Map.of("txnId", txnId)));
	}

	@Override
	public ServiceResult<TransactionModelBean> createTransaction(TransactionModelBean bean) {
		int result = commonDao.update("transaction.createTransaction", bean);
		if (result > 0) {
			return success(commonDao.selectOne("transaction.findTransactionById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<TransactionModelBean> updateTransaction(TransactionModelBean bean) {
		int result = commonDao.update("transaction.updateTransaction", bean);
		if (result > 0) {
			return success(commonDao.selectOne("transaction.findTransactionById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<EarnItemModelBean>> getEarnItemList(SearchTransactionCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("transaction.getEarnItemList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<Page<BurnItemModelBean>> getBurnItemList(SearchTransactionCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("transaction.getBurnItemList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<Page<PromotionAttrModelBean>> getPromotionAttrList(SearchTransactionCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("transaction.getPromotionAttrList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<Page<CardModelBean>> getCardList(SearchCardCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("transaction.getCardList", criteria, pageRequest));
	}
	
	@Override
	public ServiceResult<Page<ProductModelBean>> getProductList(SearchProductCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("transaction.getProductList", criteria, pageRequest));
	}
	
}
