// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface ListWebcomponentVersionUseCase {
	List<WebcomponentVersion> listAllVersionsOfWebcomponent(@NonNull String webcomponentUuid);

	Map<String, WebcomponentVersion> getLatestVersionOfEachWebcomponent(@NonNull List<String> webcomponentIds);
}
