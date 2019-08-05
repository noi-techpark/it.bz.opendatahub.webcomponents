package it.bz.opendatahub.webcomponents.dataservice.repository;

import it.bz.opendatahub.webcomponents.dataservice.data.model.WebcomponentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WebcomponentRepository extends JpaRepository<WebcomponentModel, String> {
    @Query("SELECT w FROM WebcomponentModel w WHERE LOWER(w.title) LIKE ?1")
    Page<WebcomponentModel> findAllMatchingTerm(String term, Pageable pageRequest);
}
