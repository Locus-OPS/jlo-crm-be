package th.co.locus.jlo.business.smartlink;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.smartlink.bean.SaveSmartLinkModelBean;
import th.co.locus.jlo.business.smartlink.bean.SmartLinkModelBean;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Slf4j
@Service
public class SmartLinkServiceImpl extends BaseService implements SmartLinkService {

	@Override
	public ServiceResult<SmartLinkModelBean> getSmartLinkByHashKey(String hashKey) {
		SmartLinkModelBean param = new SmartLinkModelBean();
		param.setHashKey(hashKey);
		return success(commonDao.selectOne("smartlink.getSmartLinkByHashKey", param));
	}

	@Override
	public ServiceResult<SmartLinkModelBean> getSmartLinkByHashKeyNotExpire(String hashKey) {
		SmartLinkModelBean param = new SmartLinkModelBean();
		param.setHashKey(hashKey);
		return success(commonDao.selectOne("smartlink.getSmartLinkByHashKeyNotExpire", param));
	}

	@Override
	public ServiceResult<SmartLinkModelBean> createSmartLink(SaveSmartLinkModelBean bean) {
		String hashKey = generateHashKeyUID(); //generateHashKey(bean.getHeaderId());
		log.debug("hashKey : " + hashKey);

		if (hashKey != null && !"".equals(hashKey)) {
			String link = bean.getUrlLink() + hashKey;
			bean.setHashKey(hashKey);
			bean.setUrlLink(link);

			int result = commonDao.update("smartlink.createSmartLink", bean);
			if (result > 0) {
				return success(commonDao.selectOne("smartlink.getSmartLinkByHashKey", hashKey));
			}
		}

		return fail();
	}
	
	private String generateHashKeyUID() {
		
		String hashKey =  UUID.randomUUID().toString();
		SmartLinkModelBean param = new SmartLinkModelBean();
		param.setHashKey(hashKey);
		SmartLinkModelBean result = commonDao.selectOne("smartlink.getSmartLinkByHashKey", param);
		if (result == null) {
			return hashKey;
		} else {
			return result.getHashKey();
		}
		
		
		
	}
	private String generateHashKey(Long headerId) { 
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			sb.append(chars[rnd.nextInt(chars.length)]);
		}
		String hashKey = sb.toString();
		SmartLinkModelBean param = new SmartLinkModelBean();
		param.setHashKey(hashKey);
		SmartLinkModelBean result = commonDao.selectOne("smartlink.getSmartLinkByHashKey", param);
		if (result == null) {
			return hashKey;
		} else {
			return generateHashKey(headerId);
		}
	}

}
