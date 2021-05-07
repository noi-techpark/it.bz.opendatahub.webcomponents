package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Path;

public interface WorkspaceRepository {
    byte[] readFile(@NonNull Path path) throws IOException;

    void writeFile(@NonNull Path path, byte[] data) throws IOException;

    void deletePath(@NonNull Path path);

    long getDirectorySizeInBytes(@NonNull Path path);
}
