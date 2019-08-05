package it.bz.opendatahub.webcomponents.dataservice.controller;

import it.bz.opendatahub.webcomponents.dataservice.repository.SearchtagRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.SearchtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/searchtag")
public class SearchtagController {
    private SearchtagService searchtagService;

    @Autowired
    public SearchtagController(SearchtagService searchtagService) {
        this.searchtagService = searchtagService;
    }

    @GetMapping
    public ResponseEntity<List<String>> listAll() {
        return new ResponseEntity<>(searchtagService.listAllUsedSearchtags(), HttpStatus.OK);
    }
}
