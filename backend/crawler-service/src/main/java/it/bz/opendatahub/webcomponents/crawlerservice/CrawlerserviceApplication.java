package it.bz.opendatahub.webcomponents.crawlerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CrawlerserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrawlerserviceApplication.class, args);
	}
}
