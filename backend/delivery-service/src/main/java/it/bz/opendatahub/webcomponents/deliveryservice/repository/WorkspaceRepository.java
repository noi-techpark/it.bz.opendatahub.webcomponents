// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.repository;

import java.nio.file.Path;

public interface WorkspaceRepository {
    byte[] readFile(Path path);
}
