package th.co.locus.jlo.kb.kbrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.kb.kbrating.bean.KBRatingModelBean;
@Log4j2
@RestController
@RequestMapping("api/kbrating")
public class KBRatingController extends BaseController {
	@Autowired
	private KBRatingService kbRatingService;
	
	@PostMapping(value="/createKbrating",produces = "application/json")
	public ApiResponse<KBRatingModelBean> createKBRating(@RequestBody ApiRequest<KBRatingModelBean> request) {
		try {
			request.getData().setUserId(getUserId());
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<KBRatingModelBean> resultService=this.kbRatingService.createKBRating(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getKBrating",produces = "application/json")
	public ApiResponse<KBRatingModelBean> getKBRating(@RequestBody ApiRequest<KBRatingModelBean> request) {
		try {
			request.getData().setUserId(getUserId());
			ServiceResult<KBRatingModelBean> resultService=this.kbRatingService.getKBRating(request.getData());
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
