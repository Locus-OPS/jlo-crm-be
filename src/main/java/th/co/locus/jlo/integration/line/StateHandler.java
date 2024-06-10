package th.co.locus.jlo.integration.line;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
public class StateHandler {

	LoadingCache<String, Map<String, String>> stateMapCache;
	
	@PostConstruct
	public void init() {
		stateMapCache = CacheBuilder.newBuilder().maximumSize(20).expireAfterWrite(60, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Map<String, String>>() {
					public Map<String, String> load(String type) {
						return new HashMap<String, String>();
					}
				});
	}
	
	public Map<String, String> get(String key) throws ExecutionException {
		return stateMapCache.get(key);
	}
	
	public void put(String key, Map<String, String> param) {
		stateMapCache.put(key, param);
	}
	
	public void delete(String key) {
		stateMapCache.invalidate(key);
	}
	
}
