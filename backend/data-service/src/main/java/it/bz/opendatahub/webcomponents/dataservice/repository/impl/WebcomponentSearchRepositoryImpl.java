package it.bz.opendatahub.webcomponents.dataservice.repository.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentSearchRepository;
import it.bz.opendatahub.webcomponents.dataservice.repository.mapper.WebcomponentModelMapper;
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

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public WebcomponentSearchRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SEARCH_QUERY =
            "SELECT w.* FROM webcomponent AS w WHERE " +
                    "(LOWER(w.title) LIKE :searchTerm OR LOWER(w.description) LIKE :searchTerm) " +
                    "AND w.search_tags ??| array[ :searchTags ]::varchar[] " +
                    "ORDER by w.title ASC " +
                    "LIMIT :pagesSize OFFSET :pageOffset";

    private static final String COUNT_QUERY = "SELECT count(*) FROM webcomponent AS w WHERE " +
            "(LOWER(w.title) LIKE :searchTerm OR LOWER(w.description) LIKE :searchTerm) " +
            "AND w.search_tags ??| array[ :searchTags ]::varchar[]";

    public Page<WebcomponentModel> findBySearchTermAndTags(String searchTerm, List<String> tags, Pageable pageable) {

        MapSqlParameterSource paramsData = new MapSqlParameterSource();
        paramsData.addValue("searchTerm", "%"+searchTerm.toLowerCase()+"%");
        paramsData.addValue("searchTags", tags);
        paramsData.addValue("pagesSize", pageable.getPageSize());
        paramsData.addValue("pageOffset", pageable.getOffset());

        List<WebcomponentModel> result = namedParameterJdbcTemplate.query(SEARCH_QUERY, paramsData, webcomponentModelMapper);

        Integer count = namedParameterJdbcTemplate.queryForObject(COUNT_QUERY, paramsData, Integer.class);

        return new PageImpl<>(result, pageable, count);
    }
}
