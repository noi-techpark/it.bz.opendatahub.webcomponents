package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import lombok.NonNull;

import java.nio.file.Path;

public interface ReadWorkspacePort {
	byte[] readFile(@NonNull Path path);

	long getDirectorySizeInBytes(@NonNull Path path);
}
