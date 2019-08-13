package it.bz.opendatahub.webcomponents.deliveryservice.repository;

import java.nio.file.Path;

public interface WorkspaceRepository {
    byte[] readFile(Path path);
}
