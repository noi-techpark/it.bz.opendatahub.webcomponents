package it.bz.opendatahub.webcomponents.dataservice.repository;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WebcomponentSearchRepository {
    Page<WebcomponentModel> findBySearchTermAndTags(String searchTerm, List<String> tags, Pageable pageable);
}
