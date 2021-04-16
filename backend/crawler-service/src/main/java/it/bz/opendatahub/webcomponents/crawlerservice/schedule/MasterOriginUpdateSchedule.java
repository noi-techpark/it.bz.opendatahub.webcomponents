package it.bz.opendatahub.webcomponents.crawlerservice.schedule;

import it.bz.opendatahub.webcomponents.crawlerservice.service.MasterOriginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MasterOriginUpdateSchedule {
    private final MasterOriginService masterOriginService;

    @Autowired
    public MasterOriginUpdateSchedule(final MasterOriginService masterOriginService) {
        this.masterOriginService = masterOriginService;
    }

    @Scheduled(fixedDelayString = "${application.schedule.origin}")
    public void updateOrigins() {
    	try {
			log.info("looking for changes in master");

			if(!masterOriginService.isUpToDate()) {
				log.info("updating master");

				masterOriginService.update();

				log.info("master updated");
			}
			else {
				log.info("master already up to date");
			}
    	}
        catch(Exception e) {
        	log.error(e.getMessage());
		}
    }
}
