package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import java.nio.file.Path;

public interface WriteWorkspacePort {
	void writeFile(Path path, byte[] data);

	void wipe(Path path);
}
