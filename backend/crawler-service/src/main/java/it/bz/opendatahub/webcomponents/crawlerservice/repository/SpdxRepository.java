package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpdxRepository extends JpaRepository<SpdxLicenseModel, String> {
}
