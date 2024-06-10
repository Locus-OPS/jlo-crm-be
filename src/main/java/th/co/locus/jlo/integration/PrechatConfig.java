package th.co.locus.jlo.integration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "sfdc.prechat")
@Configuration
@Component
@Data
public class PrechatConfig {

	private List<PrechatItem> items;
	
	public boolean isEmpty() {
		return items == null || items.size() == 0;
	}
	
}
