package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.repository.SearchtagRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.SearchtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchtagServiceImpl implements SearchtagService {
    private SearchtagRepository searchtagRepository;

    @Autowired
    public SearchtagServiceImpl(SearchtagRepository searchtagRepository) {
        this.searchtagRepository = searchtagRepository;
    }

    @Override
    public List<String> listAllUsedSearchtags() {
        return searchtagRepository.listAllUsedSearchtags();
    }

    @Override
    public List<String> listAllSearchtags() {
        return searchtagRepository.listAllSearchtags();
    }

    @Override
    public String findOneByName(String name) {
        Optional<String> probe = searchtagRepository.findOneByName(name);

        if(probe.isPresent()) {
            return probe.get();
        }

        throw new NotFoundException("no such entry");
    }
}
