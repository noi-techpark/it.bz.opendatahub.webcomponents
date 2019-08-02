package it.bz.opendatahub.webcomponents.dataservice.controller;

import it.bz.opendatahub.webcomponents.dataservice.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.dataservice.repository.WebcomponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/webcomponent")
public class WebcomponentController {
    private WebcomponentRepository webcomponentRepository;

    @Autowired
    public WebcomponentController(WebcomponentRepository webcomponentRepository) {
        this.webcomponentRepository = webcomponentRepository;
    }

    @GetMapping
    public ResponseEntity<List<WebcomponentModel>> listAll() {
        List<WebcomponentModel> webcomponents = webcomponentRepository.findAll();

        return new ResponseEntity<>(webcomponents, HttpStatus.OK);
    }
}
