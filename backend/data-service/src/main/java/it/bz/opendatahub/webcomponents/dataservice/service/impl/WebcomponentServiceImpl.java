package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.dataservice.converter.impl.WebcomponentConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentVersionDto;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentSearchRepository;
import it.bz.opendatahub.webcomponents.dataservice.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentService;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class WebcomponentServiceImpl implements WebcomponentService {
    private final WebcomponentSearchRepository webcomponentSearchRepository;
    private final WebcomponentRepository webcomponentRepository;
    private final WebcomponentConverter webcomponentConverter;

    private final WebcomponentVersionService webcomponentVersionService;

    private final WorkspaceRepository workspaceRepository;

    @Value("${application.deliveryBaseUrl}")
    private String deliveryBaseUrl;

    @Autowired
    public WebcomponentServiceImpl(final WebcomponentSearchRepository webcomponentSearchRepository,
                                   final WebcomponentRepository webcomponentRepository,
                                   final WebcomponentConverter webcomponentConverter,
                                   final WebcomponentVersionService webcomponentVersionService,
                                   final WorkspaceRepository workspaceRepository) {

        this.webcomponentSearchRepository = webcomponentSearchRepository;
        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentConverter = webcomponentConverter;
        this.webcomponentVersionService = webcomponentVersionService;
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Page<WebcomponentDto> listAll(Pageable pageRequest, List<String> tags, String searchTerm) {
        Page<WebcomponentModel> result = webcomponentSearchRepository.findBySearchTermAndTags(searchTerm, tags, pageRequest);

        return new PageImpl<>(webcomponentConverter.modelToDto(result.getContent()), pageRequest, result.getTotalElements());
    }

    @Override
    public WebcomponentDto findOne(String uuid) {
        Optional<WebcomponentModel> probe = webcomponentRepository.findById(uuid);

        if(probe.isPresent()) {
            return webcomponentConverter.modelToDto(probe.get());
        }

        throw new NotFoundException("no such comp");
    }

    @Override
    public WebcomponentConfiguration getConfiguration(String uuid) {
        WebcomponentDto webcomponent = findOne(uuid);

        WebcomponentVersionDto latestVersion = webcomponentVersionService.getLatestVersionOfWebcomponent(uuid);

        WebcomponentConfiguration configuration = new WebcomponentConfiguration();
        configuration.setWebcomponentUuid(uuid);
        configuration.setConfiguration(latestVersion.getConfiguration());
        configuration.setDeliveryBaseUrl(deliveryBaseUrl);

        configuration.setDist(Dist.of(webcomponent.getUuid(), latestVersion.getDist().getFiles()));

        return configuration;
    }

    @Override
    public byte[] getLogoImage(String uuid) {

        WebcomponentVersionDto latestVersion = webcomponentVersionService.getLatestVersionOfWebcomponent(uuid);

        return workspaceRepository.readFile(Paths.get(uuid, latestVersion.getVersionTag(), "wcs-logo.png"));
    }
}
