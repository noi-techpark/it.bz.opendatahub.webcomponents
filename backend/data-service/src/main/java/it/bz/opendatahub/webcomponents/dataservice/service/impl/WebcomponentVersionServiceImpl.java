package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.dataservice.converter.impl.WebcomponentVersionConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentVersionDto;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebcomponentVersionServiceImpl implements WebcomponentVersionService {
    private WebcomponentVersionRepository webcomponentVersionRepository;

    private WebcomponentVersionConverter webcomponentVersionConverter;

    @Autowired
    public WebcomponentVersionServiceImpl(WebcomponentVersionRepository webcomponentVersionRepository,
                                          WebcomponentVersionConverter webcomponentVersionConverter) {
        this.webcomponentVersionRepository = webcomponentVersionRepository;
        this.webcomponentVersionConverter = webcomponentVersionConverter;
    }

    @Override
    public List<WebcomponentVersionDto> listAllVersionsOfWebcomponent(String webcomponentId) {
        return webcomponentVersionConverter.modelToDto(webcomponentVersionRepository.findAllVersionsForWebcomponent(webcomponentId));
    }

    @Override
    public WebcomponentVersionDto getLatestVersionOfWebcomponent(String webcomponentId) {
        Optional<WebcomponentVersionModel> probe = webcomponentVersionRepository.findLatestVersionForWebcomponent(webcomponentId);

        if(probe.isPresent()) {
            return webcomponentVersionConverter.modelToDto(probe.get());
        }

        throw new NotFoundException("no version found");
    }
}
