package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;

public interface WorkspaceRepository {
    void writeFile(ByteArrayOutputStream data, Path file);
	void writeFile(byte[] data, Path file);

    void removeDirectory(Path path);

    byte[] readFile(Path path);

	long getDirectorySizeInBytes(Path path);
}
