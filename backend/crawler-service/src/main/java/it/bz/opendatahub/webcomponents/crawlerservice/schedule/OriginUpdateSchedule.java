package it.bz.opendatahub.webcomponents.crawlerservice.schedule;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.service.OriginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OriginUpdateSchedule {
    private final OriginService originService;

    @Autowired
    public OriginUpdateSchedule(final OriginService originService) {

        this.originService = originService;
    }

    @Scheduled(fixedDelayString = "${application.schedule.component}")
    public void updateWebcomponents() {
		try {
			log.info("updating origins");

			List<OriginModel> originList = originService.listAllOrigins(false);

			for(OriginModel origin : originList) {
				log.debug("updating: {}", origin.getUuid());

				originService.updateOrigin(origin);
			}

			originService.cascadeDeletedOrigins();

			log.info("origins updated");
		}
		catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    }
}
