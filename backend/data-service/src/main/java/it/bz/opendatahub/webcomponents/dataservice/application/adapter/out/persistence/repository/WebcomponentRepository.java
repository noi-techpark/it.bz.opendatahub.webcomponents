package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WebcomponentRepository extends JpaRepository<WebcomponentModel, String> {

    @Query(value = "SELECT * FROM webcomponent w WHERE (w.short_name = :id or w.uuid = :id) AND w.deleted = false", nativeQuery = true)
    Optional<WebcomponentModel> findById(String id);
}
