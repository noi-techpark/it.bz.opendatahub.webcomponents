// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentUseCase;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadWebcomponentPort {
	Webcomponent getWebcomponentById(@NonNull String uuid);

	List<Webcomponent> listAll();

	Page<Webcomponent> listPagedAndFiltered(@NonNull Pageable pageRequest, @NonNull ListWebcomponentUseCase.WebcomponentFilter filter);
}
