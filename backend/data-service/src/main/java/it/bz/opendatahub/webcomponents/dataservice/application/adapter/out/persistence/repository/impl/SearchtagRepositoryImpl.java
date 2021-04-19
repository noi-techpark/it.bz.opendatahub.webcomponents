package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.impl;

import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.SearchtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchtagRepositoryImpl implements SearchtagRepository {
    private static final String QUERY_USED_SEARCHTAGS = "SELECT DISTINCT trim('\"' FROM b::text) FROM\n" +
            "  (SELECT JSONB_ARRAY_ELEMENTS(search_tags) as b FROM webcomponent WHERE deleted=false) AS foo;";

    private static final String QUERY_ALL_SEARCHTAGS = QUERY_USED_SEARCHTAGS;

    private static final String QUERY_BY_NAME = "SELECT trim('\"' FROM b::text) FROM\n" +
            "  (SELECT JSONB_ARRAY_ELEMENTS(search_tags) as b FROM webcomponent WHERE deleted=false) AS foo WHERE b='\"?\"' LIMIT 1;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SearchtagRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> listAllUsedSearchtags() {
        return jdbcTemplate.queryForList(QUERY_USED_SEARCHTAGS, String.class);
    }

    @Override
    public List<String> listAllSearchtags() {
        return jdbcTemplate.queryForList(QUERY_ALL_SEARCHTAGS, String.class);
    }
}
