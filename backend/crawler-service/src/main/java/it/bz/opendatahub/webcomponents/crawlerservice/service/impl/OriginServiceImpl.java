package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.OriginService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OriginServiceImpl implements OriginService {
    private WebcomponentService webcomponentService;

    private OriginRepository originRepository;

    @Autowired
    public OriginServiceImpl(WebcomponentService webcomponentService,
                             OriginRepository originRepository) {

        this.webcomponentService = webcomponentService;
        this.originRepository = originRepository;
    }

    @Override
    public List<OriginModel> listAllOrigins() {
        return originRepository.findAll();
    }

    @Override
    public void updateOrigin(OriginModel origin) {
        webcomponentService.updateWebcomponentFromOrigin(origin);
    }
}
