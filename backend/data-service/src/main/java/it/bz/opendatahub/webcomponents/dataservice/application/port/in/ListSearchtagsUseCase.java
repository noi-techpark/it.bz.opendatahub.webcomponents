// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import java.util.List;

public interface ListSearchtagsUseCase {
	List<String> listAllUsedSearchtags();

	List<String> listAllSearchtags();
}
