package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebcomponentVersionRepository extends JpaRepository<WebcomponentVersionModel, WebcomponentVersionId> {
    @Query(value = "SELECT * FROM webcomponent_version AS v WHERE v.webcomponent_uuid=?1 AND v.deleted=false ORDER BY v.release_timestamp DESC LIMIT 1", nativeQuery = true)
    Optional<WebcomponentVersionModel> findLatestVersionForWebcomponent(String webcomponentId);

    @Query(value = "SELECT * FROM webcomponent_version AS v WHERE v.webcomponent_uuid IN (:webcomponentIds) AND v.version_tag IN (SELECT version_tag FROM webcomponent_version as v2 WHERE v2.webcomponent_uuid=v.webcomponent_uuid AND v2.deleted=false ORDER BY v2.release_timestamp DESC LIMIT 1) AND v.deleted=false ORDER BY v.release_timestamp DESC", nativeQuery = true)
    List<WebcomponentVersionModel> listAllVersionsForEachWebcomponentLatestFirst(List<String> webcomponentIds);

    @Query("SELECT v FROM WebcomponentVersionModel v WHERE v.webcomponentUuid=?1 AND v.deleted=false ORDER BY v.versionTag DESC")
    List<WebcomponentVersionModel> findAllVersionsForWebcomponent(String webcomponentId);

    @Query("SELECT v FROM WebcomponentVersionModel v WHERE v.lighthouseUpdateRequired=true")
    List<WebcomponentVersionModel> getAllWithScheduledLighthouseUpdate();

    @Query(value = "SELECT * FROM webcomponent_version AS v WHERE v.webcomponent_uuid=?1 AND v.version_tag=?2 AND v.deleted=false", nativeQuery = true)
    Optional<WebcomponentVersionModel> findSpecificVersionOfWebcomponent(String webcomponentId, String versionTag);

    @Transactional
	@Modifying
	@Query("UPDATE WebcomponentVersionModel m SET m.lighthouseUpdateRequired=true")
	void markAllToRefetchLighthouseMetrics();
}
