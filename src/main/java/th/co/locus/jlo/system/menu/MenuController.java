package th.co.locus.jlo.system.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;
import th.co.locus.jlo.system.menu.bean.MenuModelBean;
import th.co.locus.jlo.system.menu.bean.MenuRespModelBean;
import th.co.locus.jlo.system.menu.bean.SearchMenuModelBean;

@Api(value = "API for Menu Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @ReadPermission
    @ApiOperation(value = "Get Menu List")
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
    @ApiOperation(value = "Create Menu")
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
    @ApiOperation(value = "Update Menu")
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
    @ApiOperation(value = "Delete Menu")
    @PostMapping(value = "/deleteMenu", produces = "application/json")
    public ApiResponse<Integer> deleteMenu(@RequestBody ApiRequest<MenuModelBean> request) {
        return ApiResponse.success(menuService.deleteMenu(request.getData()).getResult());
    }
    
    @ApiOperation(value = "Get Menu Resp List")
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
