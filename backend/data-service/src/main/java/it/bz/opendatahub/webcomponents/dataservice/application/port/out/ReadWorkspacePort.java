// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import lombok.NonNull;

import java.nio.file.Path;

public interface ReadWorkspacePort {
	byte[] readFile(@NonNull Path path);

	long getDirectorySizeInBytes(@NonNull Path path);
}
