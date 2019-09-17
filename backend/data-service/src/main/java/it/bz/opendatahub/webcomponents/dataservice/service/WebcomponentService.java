package it.bz.opendatahub.webcomponents.dataservice.service;

import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WebcomponentService {
    Page<WebcomponentDto> listAll(Pageable pageRequest, List<String> tags, String term);

    WebcomponentDto findOne(String uuid);

    WebcomponentConfiguration getConfiguration(String uuid);

    WebcomponentConfiguration getConfiguration(String uuid, String versionTag);

    byte[] getLogoImage(String uuid);
}
