package it.bz.opendatahub.webcomponents.dataservice.repository;

import java.nio.file.Path;

public interface WorkspaceRepository {
    byte[] readFile(Path path);
}
