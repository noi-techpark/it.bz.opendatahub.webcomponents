package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WebcomponentRepository extends JpaRepository<WebcomponentModel, String> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE webcomponent AS m SET deleted=true WHERE EXISTS (SELECT * FROM origin as o WHERE o.uuid=m.uuid AND o.deleted=true)", nativeQuery = true)
    void markDeletedWhereOriginIsDeleted();
}
