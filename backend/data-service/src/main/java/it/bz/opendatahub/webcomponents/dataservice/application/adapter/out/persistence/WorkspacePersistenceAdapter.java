// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence;

import it.bz.opendatahub.webcomponents.common.stereotype.PersistenceAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Path;

@PersistenceAdapter
public class WorkspacePersistenceAdapter implements ReadWorkspacePort, WriteWorkspacePort {
	private final WorkspaceRepository workspaceRepository;

	public WorkspacePersistenceAdapter(WorkspaceRepository workspaceRepository) {
		this.workspaceRepository = workspaceRepository;
	}

	@Override
	public byte[] readFile(@NonNull Path path) {
		try {
			return workspaceRepository.readFile(path);
		} catch (IOException e) {
			throw new NotFoundException("file not found", e);
		}
	}

	@Override
	public void writeFile(@NonNull Path path, byte[] data) {
		try {
			workspaceRepository.writeFile(path, data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void wipe(@NonNull Path path) {
		workspaceRepository.deletePath(path);
	}

	@Override
	public long getDirectorySizeInBytes(@NonNull Path path) {
		return workspaceRepository.getDirectorySizeInBytes(path);
	}
}
