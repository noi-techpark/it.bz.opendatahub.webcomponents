package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebcomponentVersionRepository extends JpaRepository<WebcomponentVersionModel, WebcomponentVersionId> {
}
