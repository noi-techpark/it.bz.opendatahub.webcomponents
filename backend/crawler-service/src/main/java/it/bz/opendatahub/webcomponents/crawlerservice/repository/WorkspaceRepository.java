package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;

public interface WorkspaceRepository {
    void writeFile(ByteArrayOutputStream data, Path file);
}
