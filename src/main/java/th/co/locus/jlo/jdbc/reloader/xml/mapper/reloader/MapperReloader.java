package th.co.locus.jlo.jdbc.reloader.xml.mapper.reloader;

import java.util.Set;

@FunctionalInterface
public interface MapperReloader {

    void reload(Set<String> resources);
}
