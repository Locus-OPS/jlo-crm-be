package th.co.locus.jlo.kb.kbfavorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.kb.kbfavorite.bean.KBFavoriteModelBean;

@Log4j2
@RestController
@RequestMapping("api/kbfavorite")
public class KBFavoriteController extends BaseController {
	@Autowired
	private KBFavoriteService kbFavoriteService;
	
	@PostMapping(value="/getkbfavoritelist",produces = "application/json")
	public ApiPageResponse<List<KBFavoriteModelBean>> getKBFavoriteList(@RequestBody ApiPageRequest<KBFavoriteModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<KBFavoriteModelBean>> resultService=this.kbFavoriteService.getKBFavoriteList(request.getData(),pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/createkbfavorite",produces = "application/json")
	public ApiResponse<KBFavoriteModelBean> createKBFavorite(@RequestBody ApiRequest<KBFavoriteModelBean> request) {
		try {
			request.getData().setUserId(getUserId());
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<KBFavoriteModelBean> resultService=this.kbFavoriteService.createKBFavorite(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getkbfavorite",produces = "application/json")
	public ApiResponse<KBFavoriteModelBean> getKBFavorite(@RequestBody ApiRequest<KBFavoriteModelBean> request) {
		try {
			request.getData().setUserId(getUserId());
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<KBFavoriteModelBean> resultService=this.kbFavoriteService.getKBFavorite(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
}
