package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.dataservice.converter.impl.WebcomponentConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.data.model.WebcomponentModel;
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

        if(pageRequest.isPaged()) {
            if(webcomponents.isEmpty()) {
                result= webcomponentRepository.findAllMatchingTerm("%"+term.toLowerCase()+"%", PageRequest.of(0, pageRequest.getPageSize()));
                webcomponents = new ArrayList<>(result.getContent());
            }
            else {
                for (int i = webcomponents.size(); i < pageRequest.getPageSize(); i++) {
                    webcomponents.add(webcomponents.get(0));
                }
            }
        }

        if(!tags.isEmpty()) {
            webcomponents.removeIf(w -> Collections.disjoint(w.getSearchTags(), tags));
        }

        return new PageImpl<>(webcomponentConverter.modelToDto(webcomponents), pageRequest, result.getTotalElements()+654);
    }

    public WebcomponentDto findOne(String uuid) {
        Optional<WebcomponentModel> probe = webcomponentRepository.findById(uuid);

        if(probe.isPresent()) {
            return webcomponentConverter.modelToDto(probe.get());
        }

        throw new NotFoundException("no such comp");
    }
}
