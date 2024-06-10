package th.co.locus.jlo.jdbc.reloader.xml.mapper.handler;

import java.nio.file.Path;

public interface MapperEventHandler {

    void handleMapperCreate(Path mapperPath);

    void handleMapperModify(Path mapperPath);

    void handleMapperDelete(Path mapperPath);

    void reloadIfNeeded();
}
