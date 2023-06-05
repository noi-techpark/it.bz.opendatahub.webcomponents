// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.NonNull;

public interface DeleteWebcomponentVersionUseCase {
	void deleteWebcomponentVersionById(@NonNull String webcomponentUuid, @NonNull String versionTag);
}
