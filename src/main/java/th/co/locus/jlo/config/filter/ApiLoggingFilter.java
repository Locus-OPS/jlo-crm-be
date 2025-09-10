package th.co.locus.jlo.config.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import th.co.locus.jlo.common.helper.JwtHelper;
import th.co.locus.jlo.common.util.DateUtil;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Set the highest priority.
public class ApiLoggingFilter extends OncePerRequestFilter {

	// API log to write request and response data.
	private static final Logger API_LOG = LoggerFactory.getLogger("api.log");

	@Autowired
	private RequestLoggingProperties properties;
	@Autowired
	private JwtHelper jwtHelper;
	private final ObjectMapper objectMapper = getObjectMapper();

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		// Check existing trace ID, if it is not existing, create new.
		String traceId = request.getHeader("X-Trace-Id");
		if (ObjectUtils.isEmpty(traceId)) {
			traceId = UUID.randomUUID().toString(); // Create a new trace ID.
		}
		MDC.put("traceId", traceId); // Set traceId into MDC
		
		String uri = request.getRequestURI().replaceFirst(request.getContextPath(), "");
		MDC.put("request-uri", uri);
		String requestMethod = request.getMethod();
		MDC.put("request-method", requestMethod);
		String ipAddress = request.getRemoteAddr();
		MDC.put("request-ip", ipAddress);
		MDC.put("request-date", DateUtil.formatDateTime(new Date()));
		Long userId = jwtHelper.getUserId();
		MDC.put("user-id", userId != null ? userId.toString() : null);

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			logRequest(wrappedRequest);
			logResponse(wrappedResponse);
			wrappedResponse.copyBodyToResponse();
			API_LOG.info("");
		}
	}

	private void logRequest(ContentCachingRequestWrapper wrapRequest) {
        byte[] content = wrapRequest.getContentAsByteArray();
        if (content.length > 0) {
            String requestBody = new String(content, StandardCharsets.UTF_8);
            
            String maskedBody = maskSensitiveData(requestBody);
            String truncatedBody = truncatePayload(maskedBody);
            MDC.put("request-body", truncatedBody);
        }
    }

	private void logResponse(ContentCachingResponseWrapper response) {

		String responseCode = String.valueOf(response.getStatus());
		MDC.put("response-code", responseCode);
		MDC.put("response-date", DateUtil.formatDateTime(new Date()));

		// Get response body.
		byte[] content = response.getContentAsByteArray();
		if (content.length > 0) {
			String responseBody = new String(content, StandardCharsets.UTF_8);
			String maskedBody = maskSensitiveData(responseBody);
			String truncatedBody = truncatePayload(maskedBody);
			MDC.put("response-body", truncatedBody);
		}
	}

    private String truncatePayload(String payload) {
        int maxLength = properties.getMaxPayloadLength();
        if (payload.length() > maxLength) {
            return payload.substring(0, maxLength) + "...(truncated)";
        }
        return payload;
    }
	
	private String maskSensitiveData(String originalBody) {
        // ใช้ Jackson ObjectMapper เพื่อ parse JSON และทำการ mask
        // วิธีนี้จะแม่นยำกว่าการใช้ Regex
        try {
            JsonNode rootNode = objectMapper.readTree(originalBody);
            maskFields(rootNode);
            return objectMapper.writeValueAsString(rootNode);
        } catch (IOException e) {
            return originalBody;
        }
    }
	
	private void maskFields(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<String> fieldNames = objectNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (properties.getMaskedFields().contains(fieldName)) {
                    objectNode.put(fieldName, "******"); // ค่าที่ต้องการ mask
                } else {
                    maskFields(objectNode.get(fieldName));
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayNode : node) {
                maskFields(arrayNode);
            }
        }
    }

	/**
	 * Create object mapper that is disabled for duplicate JSON field.
	 * 
	 * @return the object mapper
	 */
	private ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		// Disable for duplicate JSON field.
		mapper.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
		return mapper;
	}
}
