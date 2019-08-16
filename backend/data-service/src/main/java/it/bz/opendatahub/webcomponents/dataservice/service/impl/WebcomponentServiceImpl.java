package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.dataservice.converter.impl.WebcomponentConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WebcomponentServiceImpl implements WebcomponentService {
    private WebcomponentRepository webcomponentRepository;
    private WebcomponentConverter webcomponentConverter;

    public WebcomponentServiceImpl(WebcomponentRepository webcomponentRepository, WebcomponentConverter webcomponentConverter) {
        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentConverter = webcomponentConverter;
    }

    @Override
    public Page<WebcomponentDto> listAll(Pageable pageRequest, List<String> tags, String term) {
        Page<WebcomponentModel> result = webcomponentRepository.findAllMatchingTerm("%"+term.toLowerCase()+"%", pageRequest);

        List<WebcomponentModel> webcomponents = new ArrayList<>(result.getContent());

        int i = webcomponents.size();
        if(!tags.isEmpty()) {
            webcomponents.removeIf(w -> Collections.disjoint(w.getSearchTags(), tags));
        }
        i = i - webcomponents.size();

        return new PageImpl<>(webcomponentConverter.modelToDto(webcomponents), pageRequest, result.getTotalElements()-Math.abs(i));
    }

    public WebcomponentDto findOne(String uuid) {
        Optional<WebcomponentModel> probe = webcomponentRepository.findById(uuid);

        if(probe.isPresent()) {
            return webcomponentConverter.modelToDto(probe.get());
        }

        throw new NotFoundException("no such comp");
    }
}
