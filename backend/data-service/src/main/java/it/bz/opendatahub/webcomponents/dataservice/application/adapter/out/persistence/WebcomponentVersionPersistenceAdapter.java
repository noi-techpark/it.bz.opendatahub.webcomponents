package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.stereotype.PersistenceAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.converter.WebcomponentVersionPersistenceConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
public class WebcomponentVersionPersistenceAdapter implements ReadWebcomponentVersionPort {
    private final WebcomponentVersionRepository webcomponentVersionRepository;
    private final WebcomponentVersionPersistenceConverter webcomponentVersionConverter;

	public WebcomponentVersionPersistenceAdapter(WebcomponentVersionRepository webcomponentVersionRepository, WebcomponentVersionPersistenceConverter webcomponentVersionConverter) {
		this.webcomponentVersionRepository = webcomponentVersionRepository;
		this.webcomponentVersionConverter = webcomponentVersionConverter;
	}

	@Override
    public List<WebcomponentVersion> listAllVersionsOfWebcomponent(String webcomponentUuid) {
        return webcomponentVersionConverter.convert(webcomponentVersionRepository.findAllVersionsForWebcomponent(webcomponentUuid));
    }

    @Override
    public WebcomponentVersion getLatestVersionOfWebcomponent(String webcomponentUuid) {
        Optional<WebcomponentVersionModel> probe = webcomponentVersionRepository.findLatestVersionForWebcomponent(webcomponentUuid);

        if(probe.isPresent()) {
            return webcomponentVersionConverter.convert(probe.get());
        }

        throw new NotFoundException("no version found");
    }

    @Override
    public WebcomponentVersion getSpecificVersionOfWebcomponent(String webcomponentUuid, String versionTag) {
        Optional<WebcomponentVersionModel> probe = webcomponentVersionRepository.findSpecificVersionOfWebcomponent(webcomponentUuid, versionTag);

        if(probe.isPresent()) {
            return webcomponentVersionConverter.convert(probe.get());
        }

        throw new NotFoundException("version not found");
    }
}
