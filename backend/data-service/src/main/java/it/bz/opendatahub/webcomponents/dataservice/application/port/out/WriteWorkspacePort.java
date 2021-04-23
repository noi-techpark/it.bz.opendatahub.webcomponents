package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import lombok.NonNull;

import java.nio.file.Path;

public interface WriteWorkspacePort {
	void writeFile(@NonNull Path path, byte[] data);

	void wipe(@NonNull Path path);
}
