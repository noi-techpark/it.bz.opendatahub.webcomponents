package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import java.nio.file.Path;

public interface WorkspaceRepository {
    byte[] readFile(Path path);
}
