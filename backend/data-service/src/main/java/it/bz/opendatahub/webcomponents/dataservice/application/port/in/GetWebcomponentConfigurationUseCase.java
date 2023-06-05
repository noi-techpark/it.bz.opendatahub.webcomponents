// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;
import lombok.NonNull;

public interface GetWebcomponentConfigurationUseCase {
	WebcomponentConfiguration getConfiguration(@NonNull String uuid);

	WebcomponentConfiguration getConfiguration(@NonNull String uuid, @NonNull String versionTag);
}
