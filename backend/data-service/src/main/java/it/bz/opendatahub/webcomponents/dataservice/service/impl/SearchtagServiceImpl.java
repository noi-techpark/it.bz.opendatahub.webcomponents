package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.dataservice.repository.SearchtagRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.SearchtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
