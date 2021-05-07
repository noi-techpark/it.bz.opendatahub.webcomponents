package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.WebcomponentSearchRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.mapper.WebcomponentModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WebcomponentSearchRepositoryImpl implements WebcomponentSearchRepository {
    private static final WebcomponentModelMapper webcomponentModelMapper = new WebcomponentModelMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public WebcomponentSearchRepositoryImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SEARCH_QUERY_BASE =
            "SELECT w.* FROM webcomponent AS w WHERE " +
                    "((LOWER(w.title) LIKE :searchTerm OR LOWER(w.description) LIKE :searchTerm)) " +
                    "AND w.deleted=false ";

    private static final String SEARCH_QUERY_TITLE =
            "ORDER by w.title ASC ";

    private static final String SEARCH_QUERY_LIMIT =
            "LIMIT :pagesSize OFFSET :pageOffset";

    private static final String SEARCH_QUERY_TAGS =
            "AND w.search_tags ??| array[ :searchTags ]::varchar[] ";

    private static final String SEARCH_QUERY_LATEST =
            "ORDER by (SELECT release_timestamp FROM webcomponent_version AS v WHERE v.webcomponent_uuid=w.uuid ORDER by release_timestamp DESC LIMIT 1) DESC ";

    private static final String COUNT_QUERY = "SELECT count(*) FROM webcomponent AS w WHERE " +
            "((LOWER(w.title) LIKE :searchTerm OR LOWER(w.description) LIKE :searchTerm)) AND w.deleted=false";

    private static final String COUNT_QUERY_WITH_TAGS = "SELECT count(*) FROM webcomponent AS w WHERE " +
            "((LOWER(w.title) LIKE :searchTerm OR LOWER(w.description) LIKE :searchTerm)) AND w.deleted=false " +
            "AND w.search_tags ??| array[ :searchTags ]::varchar[]";

    public Page<WebcomponentModel> findBySearchTermAndTags(String searchTerm, List<String> tags, Pageable pageable) {

        MapSqlParameterSource paramsData = new MapSqlParameterSource();
        paramsData.addValue("searchTerm", "%"+searchTerm.toLowerCase()+"%");
        paramsData.addValue("searchTags", tags);
        paramsData.addValue("pagesSize", pageable.getPageSize());
        paramsData.addValue("pageOffset", pageable.getOffset());

        String query = SEARCH_QUERY_BASE;
        String countQuery = COUNT_QUERY;
        if(!tags.isEmpty()) {
            query += SEARCH_QUERY_TAGS;
            countQuery = COUNT_QUERY_WITH_TAGS;
        }

        if(pageable.getSort().isSorted()) {
            query += SEARCH_QUERY_LATEST;
        }
        else {
            query += SEARCH_QUERY_TITLE;
        }

        query += SEARCH_QUERY_LIMIT;

        List<WebcomponentModel> result = namedParameterJdbcTemplate.query(query, paramsData, webcomponentModelMapper);

        Integer count = namedParameterJdbcTemplate.queryForObject(countQuery, paramsData, Integer.class);
        if(count == null) {
            throw new RuntimeException("count returned null");
        }

        return new PageImpl<>(result, pageable, count);
    }
}
