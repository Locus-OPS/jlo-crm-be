package th.co.locus.jlo.config.filter;

import java.util.List;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "logging.custom.request-body")
@Data
public class RequestLoggingProperties {
	private boolean enabled;
	private int maxPayloadLength;
	private Set<String> maskedFields;
	private List<String> excludePaths;
}
