package th.co.locus.jlo.business.loyalty.manualpoint;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.loyalty.manualpoint.bean.ManualPointCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.manualpoint.bean.ManualPointModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
@Slf4j
public class ManualPointServiceImpl extends BaseService implements ManualPointService {

	@Override
	public ServiceResult<Page<ManualPointModelBean>> seachManualPoint(ManualPointCriteriaModelBean bean,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("manualPoint.queryManualPoint", bean, pageRequest));
	}

	@Override
	public ServiceResult<ManualPointModelBean> insertManualPoint(ManualPointModelBean bean) {

		String resultCode = "";
		String resultDesc = "";

		/*
		 * step1 find manual adjust product for every program from code_book where
		 * member_id and bu_id
		 */

		ManualPointModelBean tmp = new ManualPointModelBean();
		if ("EARN".equals(bean.getTxnType())) {
			tmp = commonDao.selectOne("manualPoint.findProductIdByProductCodeEarn", bean);
		} else {
			tmp = commonDao.selectOne("manualPoint.findProductIdByProductCodeBurn", bean);
		}

		if (tmp != null) {
			log.info("found product code : ");
			bean.setProductId(tmp.getProductId());
			bean.setProductName(tmp.getProductName());
			bean.setProductType(tmp.getProductType());
			bean.setProductCode(tmp.getProductCode());
			bean.setLoyProductType(tmp.getLoyProductType());

			log.info(bean.toString());

			/*
			 * step2 insert to txn for manual adjust point return txnId as result
			 */
			int result = commonDao.update("manualPoint.insertManualPoint", bean);
			if (result > 0) {
				log.info("get txnId :" + bean.getTxnId());
				ManualPointCriteriaModelBean criteria = new ManualPointCriteriaModelBean();
				criteria.setTxnId(bean.getTxnId());

				return success(commonDao.selectOne("manualPoint.queryManualPoint", criteria));
			}
			return fail(resultCode, resultDesc);

		}
		log.info("NOT found product code : ");
		resultDesc = "No product code configuration for this program : [" + bean.getProgramId() + "] ";
		return fail(resultCode, resultDesc);
	}

	@Override
	public ServiceResult<ManualPointModelBean> findManualPointById(Long txnId) {
		return success(commonDao.selectOne("manualPoint.queryManualPoint", Map.of("txnId", txnId)));
	}
	

}
