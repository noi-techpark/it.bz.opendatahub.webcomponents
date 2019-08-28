package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WebcomponentVersionRepository extends JpaRepository<WebcomponentVersionModel, WebcomponentVersionId> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE webcomponent_version AS m SET deleted=true WHERE EXISTS (SELECT * FROM origin_tag AS o WHERE o.tag_name=m.version_tag AND o.origin_uuid=m.webcomponent_uuid AND o.deleted=true)", nativeQuery = true)
    void markDeletedWhereTagIsDeleted();

    @Query("SELECT m FROM WebcomponentVersionModel m WHERE m.webcomponentUuid=?1 ORDER by m.versionTag DESC")
    List<WebcomponentVersionModel> getLatestVersionForWebcomponent(String uuid);
}
