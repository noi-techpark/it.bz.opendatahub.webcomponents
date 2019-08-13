package it.bz.opendatahub.webcomponents.crawlerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan({"it.bz.opendatahub.webcomponents.common.data.model", "it.bz.opendatahub.webcomponents.crawlerservice.data.model"})
public class CrawlerserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrawlerserviceApplication.class, args);
	}
}
