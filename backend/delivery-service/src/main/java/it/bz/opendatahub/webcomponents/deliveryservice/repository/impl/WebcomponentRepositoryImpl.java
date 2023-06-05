// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.repository.impl;

import it.bz.opendatahub.webcomponents.deliveryservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.deliveryservice.repository.WebcomponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WebcomponentRepositoryImpl implements WebcomponentRepository {
    private static final String QUERY_VERSIONS =
            "SELECT version_tag FROM webcomponent_version WHERE webcomponent_uuid=? ORDER by release_timestamp DESC";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WebcomponentRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getLatestVersionOfWebcomponent(String webcomponentId) {
        List<String> versions = jdbcTemplate.queryForList(QUERY_VERSIONS, String.class, webcomponentId);

        if(versions.isEmpty()) {
            throw new NotFoundException("unable to find versions for webcomponent");
        }

        return versions.get(0);
    }
}
