package it.bz.opendatahub.webcomponents.crawlerservice.schedule;

import it.bz.opendatahub.webcomponents.crawlerservice.service.OriginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OriginUpdateSchedule {
    private OriginService originService;

    @Autowired
    public OriginUpdateSchedule(OriginService originService) {
        this.originService = originService;
    }

    @Scheduled(fixedDelayString = "${application.schedule.origin}")
    public void updateOrigins() {
        log.info("looking for changes in origins...");
        if(!originService.isUpToDate()) {
            log.info("updating origins...");
            originService.update();
        }
        log.info("origins are up to date!");
    }
}
