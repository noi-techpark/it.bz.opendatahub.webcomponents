package it.bz.opendatahub.webcomponents.dataservice.repository;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WebcomponentRepository extends JpaRepository<WebcomponentModel, String> {
    @Query("SELECT w FROM WebcomponentModel w WHERE LOWER(w.title) LIKE ?1 OR LOWER(w.description) LIKE ?1 ORDER BY w.title ASC")
    Page<WebcomponentModel> findAllMatchingSearchTerm(String searchTerm, Pageable pageRequest);
}
