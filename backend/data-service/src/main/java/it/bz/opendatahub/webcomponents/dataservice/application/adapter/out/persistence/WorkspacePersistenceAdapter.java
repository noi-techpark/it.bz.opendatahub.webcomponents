package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence;

import it.bz.opendatahub.webcomponents.common.stereotype.PersistenceAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;

import java.io.IOException;
import java.nio.file.Path;

@PersistenceAdapter
public class WorkspacePersistenceAdapter implements ReadWorkspacePort, WriteWorkspacePort {
	private final WorkspaceRepository workspaceRepository;

	public WorkspacePersistenceAdapter(WorkspaceRepository workspaceRepository) {
		this.workspaceRepository = workspaceRepository;
	}

	@Override
	public byte[] readFile(Path path) {
		try {
			return workspaceRepository.readFile(path);
		} catch (IOException e) {
			throw new NotFoundException("file not found", e);
		}
	}

	@Override
	public void writeFile(Path path, byte[] data) {
		try {
			workspaceRepository.writeFile(path, data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void wipe(Path path) {
		workspaceRepository.deletePath(path);
	}
}
