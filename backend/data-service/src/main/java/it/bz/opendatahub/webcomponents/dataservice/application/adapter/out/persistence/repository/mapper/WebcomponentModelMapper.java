// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class WebcomponentModelMapper implements RowMapper<WebcomponentModel> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WebcomponentModel mapRow(ResultSet resultSet, int i) throws SQLException {
        WebcomponentModel model = new WebcomponentModel();

        model.setUuid(resultSet.getString("uuid"));
        model.setShortName(resultSet.getString("short_name"));
        model.setTitle(resultSet.getString("title"));
        model.setDescription(resultSet.getString("description"));
        model.setDescriptionAbstract(resultSet.getString("description_abstract"));
        model.setRepositoryUrl(resultSet.getString("repository_url"));
        model.setImage(resultSet.getString("image"));
        model.setLicense(resultSet.getString("license"));
        model.setHighlighted(resultSet.getBoolean("highlighted"));
        model.setTheme(resultSet.getString("theme"));


        try {
            model.setCopyrightHolders(
                    Arrays.asList(objectMapper.readValue(resultSet.getBytes("copyright_holders"), Author[].class)));
            model.setAuthors(Arrays.asList(objectMapper.readValue(resultSet.getBytes("authors"), Author[].class)));
            model.setSearchTags(
                    Arrays.asList(objectMapper.readValue(resultSet.getBytes("search_tags"), String[].class)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return model;
    }
}
