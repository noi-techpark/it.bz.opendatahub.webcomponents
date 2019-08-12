package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.config.WorkspaceConfiguration;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WorkspaceRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class WorkspaceRepositoryImpl implements WorkspaceRepository {
    private WorkspaceConfiguration workspaceConfiguration;

    @Autowired
    public WorkspaceRepositoryImpl(WorkspaceConfiguration workspaceConfiguration) {
        this.workspaceConfiguration = workspaceConfiguration;
    }

    @Override
    public void writeFile(byte[] data, Path file) {
        Path localPath = Paths.get(workspaceConfiguration.getPath(), file.toString());

        try {
            FileUtils.writeByteArrayToFile(localPath.toFile(), data);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
