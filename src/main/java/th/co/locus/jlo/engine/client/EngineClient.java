package th.co.locus.jlo.engine.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import th.co.locus.jlo.engine.client.request.ProcessTransactionRequest;
import th.co.locus.jlo.engine.client.request.RequestMessage;
import th.co.locus.jlo.engine.client.response.ProcessTransactionResponse;
import th.co.locus.jlo.engine.client.response.ResponseMessage;

@Component
public class EngineClient {

	private static final String PATH_PROCESS_TRANSACTION = "/secured/processTransaction";
	
	/*
	private static final String PATH_PROCESS_ALL_TRANSACTION = "/secured/processAllTransaction";
	private static final String PATH_PROCESS_TRANSACTION_LIST = "/secured/processTransactionList";
	private static final String PATH_RECAL_POINT = "/secured/calculateMemberPoint";
	*/
	
	@Value("${loyalty.engine.client.id}")
	private String clientId;
	
	@Value("${loyalty.engine.client.secret}")
	private String clientSecret;
	
	@Value("${loyalty.engine.server}")
	private String serverUrl;
		
	@PostConstruct
	public void init() {
		EngineClientContext.init(serverUrl, clientId, clientSecret);
	}
	
	public ResponseMessage<ProcessTransactionResponse> processTransaction(Long txnId) {
		RequestMessage<ProcessTransactionRequest> request = new RequestMessage<>();
		ProcessTransactionRequest data = new ProcessTransactionRequest();
		data.setTxnId(txnId);		
		request.setData(data);				
		return invoke(request, serverUrl + PATH_PROCESS_TRANSACTION, ProcessTransactionResponse.class);
	}
	/*
	
	public ResponseMessage<ProcessAllTransactionResponse> processAllTransaction() {
		RequestMessage<ProcessAllTransactionRequest> request = new RequestMessage<>();
		ProcessAllTransactionRequest data = new ProcessAllTransactionRequest();
		request.setData(data);				
		return invoke(request, serverUrl + PATH_PROCESS_ALL_TRANSACTION, ProcessAllTransactionResponse.class);
	}
	

	
	public ResponseMessage<ProcessTransactionListResponse> processTransactionList(List<Long> txnIdList) {
		RequestMessage<ProcessTransactionListRequest> request = new RequestMessage<>();
		ProcessTransactionListRequest data = new ProcessTransactionListRequest();
		data.setTxnIdList(txnIdList);		
		request.setData(data);				
		return invoke(request, serverUrl + PATH_PROCESS_TRANSACTION_LIST, ProcessTransactionListResponse.class);
	}
	
	public ResponseMessage<CalculateMemberPointResponse> calculateMemberPoint(Long memberId) {
		RequestMessage<CalculateMemberPointRequest> request = new RequestMessage<>();
		CalculateMemberPointRequest data = new CalculateMemberPointRequest(); 
		data.setMemberId(memberId);		
		request.setData(data);				
		return invoke(request, serverUrl + PATH_RECAL_POINT, CalculateMemberPointResponse.class);
	}
	
	
	*/
	
	@SuppressWarnings("unchecked")
	private <T> ResponseMessage<T> invoke(Object request, String url, Class<T> responseType) {
		OAuth2RestTemplate restTemplate = EngineClientContext.getTemplate();
		ResponseMessage<T> response;
		try {
			response = restTemplate.postForObject(url, request, ResponseMessage.class);
			return response;
		} catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                restTemplate.getOAuth2ClientContext().setAccessToken(null);
                response = restTemplate.postForObject(url, request, ResponseMessage.class);
                return response;
            } else {
            	e.printStackTrace();
    			return null;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
