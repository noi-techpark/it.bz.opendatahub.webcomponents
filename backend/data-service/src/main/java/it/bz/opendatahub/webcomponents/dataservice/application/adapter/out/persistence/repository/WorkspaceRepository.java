package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import java.io.IOException;
import java.nio.file.Path;

public interface WorkspaceRepository {
    byte[] readFile(Path path) throws IOException;

    void writeFile(Path path, byte[] data) throws IOException;

    void deletePath(Path path);
}
