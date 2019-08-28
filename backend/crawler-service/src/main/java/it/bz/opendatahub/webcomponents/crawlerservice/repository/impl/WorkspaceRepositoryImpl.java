package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.config.WorkspaceConfiguration;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.NotFoundException;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WorkspaceRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
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
    public void writeFile(ByteArrayOutputStream data, Path file) {
        Path localPath = Paths.get(workspaceConfiguration.getPath(), file.toString());

        try {
            FileUtils.writeByteArrayToFile(localPath.toFile(), data.toByteArray());
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }
    }

    @Override
    public void removeDirectory(Path path) {
        try {
            FileUtils.deleteDirectory(path.toFile());
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }
    }

    @Override
    public byte[] readFile(Path path) {
        Path localPath = Paths.get(workspaceConfiguration.getPath(), path.toString());

        try {
            return FileUtils.readFileToByteArray(localPath.toFile());
        }
        catch (IOException e) {
            throw new NotFoundException("requested file is unavailable");
        }
    }
}
