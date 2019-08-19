package it.bz.opendatahub.webcomponents.deliveryservice.controller;

import it.bz.opendatahub.webcomponents.deliveryservice.data.DistFile;
import it.bz.opendatahub.webcomponents.deliveryservice.service.DistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dist")
public class DistController {
    private DistService distService;

    @Autowired
    public DistController(DistService distService) {
        this.distService = distService;
    }

    @GetMapping(value = "/{webcomponentId}/{file}", produces = {"application/javascript", "text/css", "text/plain"})
    public ResponseEntity<byte[]> getDistLatest(@PathVariable String webcomponentId, @PathVariable String file) {
        DistFile distFile = getDist(webcomponentId, file, null);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType(distFile.getMimetype()));

        return new ResponseEntity<>(distFile.getPayload(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/{webcomponentId}/{version}/{file}", produces = {"application/javascript", "text/css", "text/plain"})
    public ResponseEntity<byte[]> getDistVersioned(@PathVariable String webcomponentId, @PathVariable String version, @PathVariable String file) {
        DistFile distFile = getDist(webcomponentId, file, version);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType(distFile.getMimetype()));

        return new ResponseEntity<>(distFile.getPayload(), responseHeaders, HttpStatus.OK);
    }

    private DistFile getDist(String webcomponentId, String file, String version) {
        return distService.getDistFile(webcomponentId, file, version);
    }
}
