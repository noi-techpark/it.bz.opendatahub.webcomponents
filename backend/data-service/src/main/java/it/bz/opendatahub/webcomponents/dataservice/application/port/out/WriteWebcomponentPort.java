// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import lombok.NonNull;

public interface WriteWebcomponentPort {
	Webcomponent saveWebcomponent(@NonNull Webcomponent webcomponent);
}
