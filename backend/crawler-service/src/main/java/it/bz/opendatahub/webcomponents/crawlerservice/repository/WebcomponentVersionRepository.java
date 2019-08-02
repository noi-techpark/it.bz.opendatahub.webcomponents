package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentVersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebcomponentVersionRepository extends JpaRepository<WebcomponentVersionModel, String> {
    @Query("SELECT v FROM WebcomponentVersionModel v WHERE v.webcomponentUuid=?1 AND v.versionTag=?2")
    Optional<WebcomponentVersionModel> findByUuidAndTag(String uuid, String tag);
}
