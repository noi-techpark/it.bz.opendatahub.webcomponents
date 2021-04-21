package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence;

import it.bz.opendatahub.webcomponents.common.converter.ConverterUtils;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import it.bz.opendatahub.webcomponents.common.stereotype.PersistenceAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.converter.WebcomponentVersionPersistenceConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.val;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
public class WebcomponentVersionPersistenceAdapter implements ReadWebcomponentVersionPort, WriteWebcomponentVersionPort {
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
	public List<WebcomponentVersion> getAllWithScheduledLighthouseUpdate() {
		return webcomponentVersionConverter.convert(webcomponentVersionRepository.getAllWithScheduledLighthouseUpdate());
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

	@Override
	public WebcomponentVersion saveWebcomponentVersion(WebcomponentVersion webcomponentVersion) {
		val probe = webcomponentVersionRepository.findById(
			WebcomponentVersionId.of(
				webcomponentVersion.getWebcomponentUuid(),
				webcomponentVersion.getVersionTag()
			)
		);
		WebcomponentVersionModel model;
		if(probe.isPresent()) {
			model = probe.get();
		}
		else {
			model = new WebcomponentVersionModel();
		}

		ConverterUtils.copyProperties(webcomponentVersion, model);

		model = webcomponentVersionRepository.save(model);

		return webcomponentVersionConverter.convert(model);
	}
}
