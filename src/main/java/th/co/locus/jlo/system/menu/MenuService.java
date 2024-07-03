package th.co.locus.jlo.system.menu;

import java.util.List;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.bean.TokenMenuRespModelBean;
import th.co.locus.jlo.system.menu.bean.MenuModelBean;
import th.co.locus.jlo.system.menu.bean.MenuRespModelBean;
import th.co.locus.jlo.system.menu.bean.SearchMenuModelBean;

public interface MenuService {

    public ServiceResult<Page<MenuModelBean>> getMenuList(SearchMenuModelBean bean, PageRequest pageRequest);

    public ServiceResult<MenuModelBean> createMenu(MenuModelBean bean);

    public ServiceResult<MenuModelBean> updateMenu(MenuModelBean bean);

    public ServiceResult<Integer> deleteMenu(MenuModelBean bean);
    
    public ServiceResult<List<MenuRespModelBean>> getMenuListByRole(String roleCode);
    
    public ServiceResult<List<TokenMenuRespModelBean>> getTokenMenuListByRole(String roleCode);
}
