package it.bz.opendatahub.webcomponents.dataservice.controller;

import it.bz.opendatahub.webcomponents.common.data.rest.Webcomponent;
import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentEntry;
import it.bz.opendatahub.webcomponents.dataservice.converter.impl.WebcomponentConverter;
import it.bz.opendatahub.webcomponents.dataservice.converter.impl.WebcomponentEntryConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/webcomponent")
public class WebcomponentController {
    private final WebcomponentService webcomponentService;
    private final WebcomponentEntryConverter webcomponentEntryConverter;
    private final WebcomponentConverter webcomponentConverter;

    @Autowired
    public WebcomponentController(final WebcomponentService webcomponentService,
                                  final WebcomponentEntryConverter webcomponentEntryConverter,
                                  final WebcomponentConverter webcomponentConverter) {

        this.webcomponentService = webcomponentService;
        this.webcomponentEntryConverter = webcomponentEntryConverter;
        this.webcomponentConverter = webcomponentConverter;
    }

    @GetMapping
    public ResponseEntity<Page<WebcomponentEntry>> find(
                                                           @RequestParam(name = "tags", required = false) String[] tags,
                                                           @RequestParam(name = "searchTerm", required = false, defaultValue = "") String searchTerm,
                                                           @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer requestPageNumber,
                                                           @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer requestPageSize,
                                                           @RequestParam(name = "latest", required = false, defaultValue = "false") Boolean latest
                                                           ) {

        Pageable pageRequest;
        if(latest) {
            pageRequest = PageRequest.of(requestPageNumber, requestPageSize, Sort.by("latest"));
        }
        else {
            pageRequest = PageRequest.of(requestPageNumber, requestPageSize);
        }

        List<String> tagList = Collections.emptyList();
        if(tags != null) {
            tagList = Arrays.asList(tags);
        }

        Page<WebcomponentDto> resultPage = webcomponentService.listAll(pageRequest, tagList, searchTerm);

        return new ResponseEntity<>(new PageImpl(webcomponentEntryConverter.dtoToRest(resultPage.getContent()), pageRequest, resultPage.getTotalElements()), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Webcomponent> getOne(@PathVariable String uuid) {
        return new ResponseEntity<>(webcomponentConverter.dtoToRest(webcomponentService.findOne(uuid)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/config")
    public ResponseEntity<WebcomponentConfiguration> getConfiguration(@PathVariable String uuid) {
        return new ResponseEntity<>(webcomponentService.getConfiguration(uuid), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/config/{versionTag}")
    public ResponseEntity<WebcomponentConfiguration> getConfigurationForVersion(@PathVariable String uuid, @PathVariable String versionTag) {
        return new ResponseEntity<>(webcomponentService.getConfiguration(uuid, versionTag), HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}/logo", produces = "image/png")
    public ResponseEntity<byte[]> getLogoImage(@PathVariable String uuid) {
        return new ResponseEntity<>(webcomponentService.getLogoImage(uuid), HttpStatus.OK);
	}

}
