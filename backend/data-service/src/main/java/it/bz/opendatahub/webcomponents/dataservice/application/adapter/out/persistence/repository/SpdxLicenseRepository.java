package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpdxLicenseRepository extends JpaRepository<SpdxLicenseModel, String> {
}
