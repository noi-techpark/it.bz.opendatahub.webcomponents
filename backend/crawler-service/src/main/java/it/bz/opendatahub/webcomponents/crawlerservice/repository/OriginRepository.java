package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OriginRepository extends JpaRepository<OriginModel, String> {

    @Transactional
    @Modifying
    @Query("UPDATE OriginModel m SET m.deleted=true WHERE m.uuid NOT IN ?1")
    void markAllNotInListAsDeleted(List<String> origins);

    @Query("SELECT m FROM OriginModel m WHERE m.deleted=?1")
    List<OriginModel> findAllByDeletedFlag(boolean deleted);
}
