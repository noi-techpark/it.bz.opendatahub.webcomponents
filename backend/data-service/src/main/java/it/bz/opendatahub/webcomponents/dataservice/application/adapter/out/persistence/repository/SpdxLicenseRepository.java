package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpdxLicenseRepository extends JpaRepository<SpdxLicenseModel, String> {
	@Query("SELECT m FROM SpdxLicenseModel  m WHERE m.licenseId IN (:ids)")
	List<SpdxLicenseModel> listByIds(List<String> ids);
}
