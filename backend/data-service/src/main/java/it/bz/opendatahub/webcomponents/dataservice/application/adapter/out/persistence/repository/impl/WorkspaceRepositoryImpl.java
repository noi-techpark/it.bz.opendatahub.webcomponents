package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.impl;

import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.dataservice.config.WorkspaceConfiguration;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class WorkspaceRepositoryImpl implements WorkspaceRepository {
    private final WorkspaceConfiguration workspaceConfiguration;

    @Autowired
    public WorkspaceRepositoryImpl(final WorkspaceConfiguration workspaceConfiguration) {
        this.workspaceConfiguration = workspaceConfiguration;
    }

    @Override
    public byte[] readFile(@NonNull Path path) throws IOException {
        val localPath = Paths.get(workspaceConfiguration.getPath(), path.toString());

        return FileUtils.readFileToByteArray(localPath.toFile());
    }

	@Override
	public void writeFile(@NonNull Path path, byte[] data) throws IOException {
		val localPath = Paths.get(workspaceConfiguration.getPath(), path.toString());

		FileUtils.writeByteArrayToFile(localPath.toFile(), data);
	}

	@Override
	public void deletePath(@NonNull Path path) {
		val localPath = Paths.get(workspaceConfiguration.getPath(), path.toString());

		FileUtils.deleteQuietly(localPath.toFile());
	}

	@Override
	public long getDirectorySizeInBytes(@NonNull Path path) throws IllegalArgumentException {
		val localPath = Paths.get(workspaceConfiguration.getPath(), path.toString());

		val file = localPath.toFile();

		if(!file.exists()) {
			return 0;
		}

		return FileUtils.sizeOfDirectory(file);
	}
}
