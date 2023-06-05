// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.repository.impl;

import it.bz.opendatahub.webcomponents.deliveryservice.config.WorkspaceConfiguration;
import it.bz.opendatahub.webcomponents.deliveryservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.deliveryservice.repository.WorkspaceRepository;
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
    public byte[] readFile(Path path) {
        Path localPath = Paths.get(workspaceConfiguration.getPath(), path.toString());

        try {
            return FileUtils.readFileToByteArray(localPath.toFile());
        } catch (IOException e) {
            throw new NotFoundException("requested file is unavailable: " + localPath.toFile());
        }
    }
}
