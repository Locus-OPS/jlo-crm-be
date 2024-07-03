package th.co.locus.jlo.util.selector;

import java.util.List;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

public interface SelectorService {

	public ServiceResult<List<SelectorModelBean>> getProgram();
	public ServiceResult<List<SelectorModelBean>> getProvince(String parent);
	public ServiceResult<List<SelectorModelBean>> getDistrict(String parent);
	public ServiceResult<List<SelectorModelBean>> getSubDistrict(String parent);
	public ServiceResult<List<SelectorModelBean>> getPostCode(String parent);
	public ServiceResult<List<SelectorModelBean>> getPostCodeDetail(String data);

}
