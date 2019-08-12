package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import java.nio.file.Path;

public interface WorkspaceRepository {
    void writeFile(byte[] data, Path file);
}
