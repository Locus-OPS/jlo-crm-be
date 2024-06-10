package th.co.locus.jlo.integration.sfdc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linecorp.bot.client.LineMessagingClient;

import th.co.locus.jlo.integration.LineSfdcConfig;
import th.co.locus.jlo.integration.PrechatConfig;

@Component
public class SfdcSessionHandler {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	@Autowired
	private LineSfdcConfig lineSfdcConfig;
	
	@Autowired
	private PrechatConfig prechatConfig;
	
	private static Map<String, SfdcConnector> session = new HashMap<>();

	public void start(String lineId, Map<String, String> paramMap) {
		SfdcConnector connector = new SfdcConnector(this, lineId, paramMap, lineMessagingClient, lineSfdcConfig, prechatConfig);
		connector.init();
		session.put(lineId, connector);
	}
	
	public void destroy(String lineId) {
		System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>> SfdcSessionHandler : destroy");
		session.remove(lineId);
	}
	
	public void endChat(String lineId) {
		System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>> SfdcSessionHandler : endChat");
		SfdcConnector connector = session.get(lineId);
		if (connector != null) {
			connector.sendChatEnd();
		}
		destroy(lineId);
	}
	
	public void checkAndSendMessage(String lineId, String text) {
		System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>> SfdcSessionHandler : checkAndSendMessage");
		SfdcConnector connector = session.get(lineId);
		if (connector != null) {
			connector.sendMessage(text);
		}
	}
	
	public boolean isOnline(String lineId) {
		System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>> SfdcSessionHandler : isOnline");
		return session.get(lineId) != null;
	}
	
}
