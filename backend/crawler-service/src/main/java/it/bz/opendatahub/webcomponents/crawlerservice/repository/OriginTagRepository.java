package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginTagModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.id.OriginTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OriginTagRepository extends JpaRepository<OriginTagModel, OriginTagId> {

    @Transactional
    @Modifying
    @Query("UPDATE OriginTagModel m SET m.deleted=true WHERE m.originUuid=?1 AND m.tagName NOT IN ?2")
    void markAllTagsNotInListAsDeleted(String originUuid, List<String> tags);
}
