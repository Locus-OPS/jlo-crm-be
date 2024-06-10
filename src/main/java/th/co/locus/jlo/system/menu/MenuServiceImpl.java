package th.co.locus.jlo.system.menu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.system.menu.bean.MenuModelBean;
import th.co.locus.jlo.system.menu.bean.MenuRespModelBean;
import th.co.locus.jlo.system.menu.bean.SearchMenuModelBean;
import th.co.locus.jlo.system.menu.bean.TokenMenuRespModelBean;
import th.co.locus.jlo.util.selector.SelectorService;

@Service
public class MenuServiceImpl extends BaseService implements MenuService {

	@Autowired
	private SelectorService selectorService;
	
    @Override
    public ServiceResult<Page<MenuModelBean>> getMenuList(SearchMenuModelBean bean, PageRequest pageRequest) {
        return success(commonDao.selectPage("menu.getMenuList", bean, pageRequest));
    }

    @Override
    public ServiceResult<MenuModelBean> createMenu(MenuModelBean bean) {
        int result = commonDao.update("menu.createMenu", bean);
        if (result > 0) {
        	selectorService.clearParentMenuCache();
            return success(commonDao.selectOne("menu.findMenuById", bean));
        }
        return fail();
    }

    @Override
    public ServiceResult<MenuModelBean> updateMenu(MenuModelBean bean) {
        int result = commonDao.update("menu.updateMenu", bean);
        if (result > 0) {
        	selectorService.clearParentMenuCache();
            return success(commonDao.selectOne("menu.findMenuById", bean));
        }
        return fail();
    }

    @Override
    public ServiceResult<Integer> deleteMenu(MenuModelBean bean) {
        return success(commonDao.delete("menu.deleteMenu", bean));
    }

	@Override
	public ServiceResult<List<MenuRespModelBean>> getMenuListByRole(String roleCode) {
		return success(commonDao.selectList("menu.getMenuRespList", Map.of("roleCode", roleCode)));
	}
	
	@Override
	public ServiceResult<List<TokenMenuRespModelBean>> getTokenMenuListByRole(String roleCode) {
		return success(commonDao.selectList("menu.getTokenMenuRespList", Map.of("roleCode", roleCode)));
	}
}
