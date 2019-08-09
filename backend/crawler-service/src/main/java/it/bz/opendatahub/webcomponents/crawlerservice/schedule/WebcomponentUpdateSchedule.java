package it.bz.opendatahub.webcomponents.crawlerservice.schedule;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.service.OriginService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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
