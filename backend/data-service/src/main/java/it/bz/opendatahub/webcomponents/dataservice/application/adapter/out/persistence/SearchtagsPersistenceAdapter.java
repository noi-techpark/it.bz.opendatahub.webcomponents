// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence;

import it.bz.opendatahub.webcomponents.common.stereotype.PersistenceAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.SearchtagRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadSearchtagsPort;

import java.util.List;

@PersistenceAdapter
public class SearchtagsPersistenceAdapter implements ReadSearchtagsPort {
	private final SearchtagRepository searchtagRepository;

	public SearchtagsPersistenceAdapter(SearchtagRepository searchtagRepository) {
		this.searchtagRepository = searchtagRepository;
	}

	@Override
	public List<String> listAllUsedSearchtags() {
		return searchtagRepository.listAllUsedSearchtags();
	}

	@Override
	public List<String> listAllSearchtags() {
		return searchtagRepository.listAllSearchtags();
	}
}
