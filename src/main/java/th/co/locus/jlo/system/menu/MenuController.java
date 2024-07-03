package th.co.locus.jlo.system.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.system.menu.bean.MenuModelBean;
import th.co.locus.jlo.system.menu.bean.MenuRespModelBean;
import th.co.locus.jlo.system.menu.bean.SearchMenuModelBean;

@RestController
@RequestMapping("api/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @ReadPermission
    @PostMapping(value = "/getMenuList", produces = "application/json")
    public ApiPageResponse<List<MenuModelBean>> getMenuList(@RequestBody ApiPageRequest<SearchMenuModelBean> request) {
        PageRequest pageRequest = getPageRequest(request);
        ServiceResult<Page<MenuModelBean>> serviceResult = menuService.getMenuList(request.getData(), pageRequest);
        if (serviceResult.isSuccess()) {
            return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
        } else {
            return ApiPageResponse.fail();
        }
    }

    @WritePermission
    @PostMapping(value = "/createMenu", produces = "application/json")
    public ApiResponse<MenuModelBean> createMenu(@RequestBody ApiRequest<MenuModelBean> request) {
        request.getData().setCreatedBy(getUserId());
        ServiceResult<MenuModelBean> serviceResult;
        serviceResult = menuService.createMenu(request.getData());
        if (serviceResult.isSuccess()) {
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.fail();
    }

    @WritePermission
    @PostMapping(value = "/updateMenu", produces = "application/json")
    public ApiResponse<MenuModelBean> updateMenu(@RequestBody ApiRequest<MenuModelBean> request) {
        request.getData().setUpdatedBy(getUserId());
        ServiceResult<MenuModelBean> serviceResult;
        serviceResult = menuService.updateMenu(request.getData());
        if (serviceResult.isSuccess()) {
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.fail();
    }

    @WritePermission
    @PostMapping(value = "/deleteMenu", produces = "application/json")
    public ApiResponse<Integer> deleteMenu(@RequestBody ApiRequest<MenuModelBean> request) {
        return ApiResponse.success(menuService.deleteMenu(request.getData()).getResult());
    }
    
    @PostMapping(value = "/getMenuRespList", produces = "application/json")
    public ApiResponse<List<MenuRespModelBean>> getMenuRespList(@RequestBody ApiRequest<String> request) {
        ServiceResult<List<MenuRespModelBean>> serviceResult = menuService.getMenuListByRole(getRoleCode());
        if (serviceResult.isSuccess()) {
            return ApiResponse.success(serviceResult.getResult());
        } else {
            return ApiResponse.fail();
        }
    }
}
