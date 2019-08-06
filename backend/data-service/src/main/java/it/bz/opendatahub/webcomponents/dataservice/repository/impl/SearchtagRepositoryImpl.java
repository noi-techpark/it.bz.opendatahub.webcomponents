package it.bz.opendatahub.webcomponents.dataservice.repository.impl;

import it.bz.opendatahub.webcomponents.dataservice.repository.SearchtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchtagRepositoryImpl implements SearchtagRepository {
    private static final String QUERY_USED_SEARCHTAGS = "SELECT name FROM searchtag WHERE hidden=false AND EXISTS(SELECT * FROM webcomponent WHERE search_tags ? name)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SearchtagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> listAllUsedSearchtags() {
        return jdbcTemplate.queryForList(QUERY_USED_SEARCHTAGS, String.class);
    }
}
