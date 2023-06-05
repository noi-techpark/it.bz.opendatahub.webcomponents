// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import java.util.List;

public interface SearchtagRepository {
    List<String> listAllUsedSearchtags();

    List<String> listAllSearchtags();
}
