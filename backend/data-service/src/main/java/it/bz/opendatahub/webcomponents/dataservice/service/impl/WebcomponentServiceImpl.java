package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.dataservice.converter.impl.WebcomponentConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentSearchRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebcomponentServiceImpl implements WebcomponentService {
    private WebcomponentSearchRepository webcomponentSearchRepository;
    private WebcomponentRepository webcomponentRepository;
    private WebcomponentConverter webcomponentConverter;

    @Value("${application.deliveryBaseUrl}")
    private String deliveryBaseUrl;

    @Autowired
    public WebcomponentServiceImpl(WebcomponentSearchRepository webcomponentSearchRepository,
                                   WebcomponentRepository webcomponentRepository,
                                   WebcomponentConverter webcomponentConverter) {

        this.webcomponentSearchRepository = webcomponentSearchRepository;
        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentConverter = webcomponentConverter;
    }

    @Override
    public Page<WebcomponentDto> listAll(Pageable pageRequest, List<String> tags, String searchTerm) {
        Page<WebcomponentModel> result;
        if(!tags.isEmpty()) {
            result = webcomponentSearchRepository.findBySearchTermAndTags(searchTerm, tags, pageRequest);
        }
        else {
            result = webcomponentRepository.findAllMatchingSearchTerm("%"+searchTerm.toLowerCase()+"%", pageRequest);
        }

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

        WebcomponentConfiguration configuration = new WebcomponentConfiguration();
        configuration.setWebcomponentUuid(uuid);
        configuration.setConfiguration(webcomponent.getConfiguration());
        configuration.setDeliveryBaseUrl(deliveryBaseUrl);

        configuration.setDist(Dist.of(webcomponent.getUuid(), webcomponent.getDist().getFiles()));

        return configuration;
    }

    @Override
    public byte[] getLogoImage(String uuid) {


        return new byte[0];
    }
}
