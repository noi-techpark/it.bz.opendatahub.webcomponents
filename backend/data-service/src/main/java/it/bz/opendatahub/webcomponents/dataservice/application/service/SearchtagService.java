// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListSearchtagsUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadSearchtagsPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchtagService implements ListSearchtagsUseCase {
    private final ReadSearchtagsPort readSearchtagsPort;

	public SearchtagService(ReadSearchtagsPort readSearchtagsPort) {
		this.readSearchtagsPort = readSearchtagsPort;
	}

	@Override
    public List<String> listAllUsedSearchtags() {
        return readSearchtagsPort.listAllUsedSearchtags();
    }

    @Override
    public List<String> listAllSearchtags() {
        return readSearchtagsPort.listAllSearchtags();
    }
}
