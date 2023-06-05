// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import lombok.NonNull;

import java.nio.file.Path;

public interface WriteWorkspacePort {
	void writeFile(@NonNull Path path, byte[] data);

	void wipe(@NonNull Path path);
}
