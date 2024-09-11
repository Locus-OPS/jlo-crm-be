package th.co.locus.jlo.integration.chat.line;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.integration.chat.bean.ChatChannelConfig;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LineMessagingService extends BaseService {

    private static final int DEFAULT_TIME = 10;
    private static final TimeUnit DEFAULT_UNIT = TimeUnit.MINUTES;

    LoadingCache<String, ChatChannelConfig> lineChannelConfigCache;

    @PostConstruct
    public void init() {
        this.lineChannelConfigCache = CacheBuilder.newBuilder().maximumSize(20L).expireAfterWrite(DEFAULT_TIME, DEFAULT_UNIT).build(new CacheLoader<String, ChatChannelConfig>() {
            public ChatChannelConfig load(@NotNull String channelKey) {
                return commonDao.selectOne("integration-chat-line.getLineChannelConfig", Map.of("channelKey", channelKey));
            }
        });
    }

    public ChatChannelConfig getLineChannelConfig(String channelKey) throws ExecutionException {
        return lineChannelConfigCache.get(channelKey);
    }

}
