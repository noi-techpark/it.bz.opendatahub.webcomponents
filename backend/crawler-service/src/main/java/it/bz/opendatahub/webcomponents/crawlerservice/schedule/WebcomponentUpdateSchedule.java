package it.bz.opendatahub.webcomponents.crawlerservice.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentVersionFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.impl.GithubApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.OriginService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class WebcomponentUpdateSchedule {
    private OriginService originService;
    private WebcomponentService webcomponentService;

    @Autowired
    public WebcomponentUpdateSchedule(OriginService originService,
                                      WebcomponentService webcomponentService) {

        this.originService = originService;
        this.webcomponentService = webcomponentService;
    }

    @Scheduled(fixedDelayString = "${application.schedule.component}")
    public void updateWebcomponents() {
        log.info("comps update");

        List<OriginModel> originList = originService.listAllOrigins();

        for(OriginModel origin : originList) {
            webcomponentService.updateOrigin(origin);
        }
    }
}
