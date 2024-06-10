package th.co.locus.jlo.jdbc.reloader.xml.mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.locus.jlo.jdbc.reloader.utils.ConfigurationHelper;
import th.co.locus.jlo.jdbc.reloader.xml.mapper.handler.XmlMapperEventHandler;
import th.co.locus.jlo.jdbc.reloader.xml.mapper.reloader.XmlMapperReloader;
import th.co.locus.jlo.jdbc.reloader.xml.mapper.watcher.DirectoryWatcher;

public class MybatisXmlMapperReloaderFactory {

    private static final Logger log = LoggerFactory.getLogger(MybatisXmlMapperReloaderFactory.class);
    private static final Pattern PATTERN = Pattern.compile("file \\[(.*?\\.xml)]");
    private DirectoryWatcher watchService;
    private SqlSessionFactory sqlSessionFactory;
    private ScheduledExecutorService executorService;

    public void destroy() {
        if (executorService != null) {
            executorService.shutdown();
        }

        try {
            if (this.watchService != null) {
                this.watchService.close();
            }
        } catch (IOException e) {
            log.error("shutdown watch service error", e);
        }

    }

    public void init() throws Exception {

        Configuration configuration = sqlSessionFactory.getConfiguration();

        ConfigurationHelper configurationHelper = new ConfigurationHelper(configuration);
        Set<String> loadedResources = configurationHelper.readField(ConfigurationHelper.LOADED_RESOURCE_MAP_NAME);

        Set<String> resources = Collections.unmodifiableSet(loadedResources.stream().map(PATTERN::matcher).filter(Matcher::find).map(m -> m.group(1)).collect(Collectors.toSet()));

        final XmlMapperReloader scanner = new XmlMapperReloader(configurationHelper);

        String root = StringUtils.getCommonPrefix(Collections.unmodifiableSet(loadedResources.stream().map(PATTERN::matcher).filter(Matcher::find).map(m -> m.group(1)).collect(Collectors.toSet())).toArray(new String[]{}));
        Path path = Paths.get(root);

        boolean regularFile = Files.isRegularFile(path);
        if (regularFile) {
            path = path.getParent();
        }

        watchService = new DirectoryWatcher(() -> new XmlMapperEventHandler(scanner, resources), true, path);


        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(watchService::processEvents, 5L, 1L, TimeUnit.SECONDS);

        log.info("Start MyBatis Watcher ...");
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}