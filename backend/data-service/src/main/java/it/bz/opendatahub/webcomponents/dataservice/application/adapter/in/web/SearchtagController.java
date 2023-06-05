// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListSearchtagsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequestMapping("/searchtag")
public class SearchtagController {
    private final ListSearchtagsUseCase listSearchtagsUseCase;

	public SearchtagController(ListSearchtagsUseCase listSearchtagsUseCase) {
		this.listSearchtagsUseCase = listSearchtagsUseCase;
	}

	@GetMapping
    public List<String> listAllUsed() {
        return listSearchtagsUseCase.listAllUsedSearchtags();
    }
}
