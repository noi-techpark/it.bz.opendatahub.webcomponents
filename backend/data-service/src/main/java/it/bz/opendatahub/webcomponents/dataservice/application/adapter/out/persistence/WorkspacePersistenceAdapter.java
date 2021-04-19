package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence;

import it.bz.opendatahub.webcomponents.common.stereotype.PersistenceAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWorkspacePort;

import java.nio.file.Path;

@PersistenceAdapter
public class WorkspacePersistenceAdapter implements ReadWorkspacePort {
	private final WorkspaceRepository workspaceRepository;

	public WorkspacePersistenceAdapter(WorkspaceRepository workspaceRepository) {
		this.workspaceRepository = workspaceRepository;
	}

	@Override
	public byte[] readFile(Path path) {
		return workspaceRepository.readFile(path);
	}
}
