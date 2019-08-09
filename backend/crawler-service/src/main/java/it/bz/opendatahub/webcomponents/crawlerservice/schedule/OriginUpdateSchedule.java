package it.bz.opendatahub.webcomponents.crawlerservice.schedule;

import it.bz.opendatahub.webcomponents.crawlerservice.service.MasterOriginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OriginUpdateSchedule {
    private MasterOriginService masterOriginService;

    @Autowired
    public OriginUpdateSchedule(MasterOriginService masterOriginService) {
        this.masterOriginService = masterOriginService;
    }

    @Scheduled(fixedDelayString = "${application.schedule.origin}")
    public void updateOrigins() {
        log.info("looking for changes in origins...");

        if(!masterOriginService.isUpToDate()) {
            log.info("updating origins...");

            masterOriginService.update();
        }

        log.info("origins are up to date!");
    }
}
