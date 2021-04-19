package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import java.nio.file.Path;

public interface ReadWorkspacePort {
	byte[] readFile(Path path);
}
