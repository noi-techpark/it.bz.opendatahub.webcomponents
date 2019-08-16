package it.bz.opendatahub.webcomponents.dataservice.repository.impl;

import it.bz.opendatahub.webcomponents.dataservice.repository.SearchtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SearchtagRepositoryImpl implements SearchtagRepository {
    private static final String QUERY_USED_SEARCHTAGS = "SELECT name FROM searchtag WHERE hidden=false AND EXISTS(SELECT * FROM webcomponent WHERE search_tags ? name)";

    private static final String QUERY_ALL_SEARCHTAGS = "SELECT name FROM searchtag WHERE hidden=false";

    private static final String QUERY_BY_NAME = "SELECT name FROM searchtag WHERE name=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SearchtagRepositoryImpl(JdbcTemplate jdbcTemplate) {
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

    @Override
    public Optional<String> findOneByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_BY_NAME, String.class, name));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
