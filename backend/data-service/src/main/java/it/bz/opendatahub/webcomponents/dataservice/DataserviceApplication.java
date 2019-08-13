package it.bz.opendatahub.webcomponents.dataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"it.bz.opendatahub.webcomponents.common.data.model", "it.bz.opendatahub.webcomponents.dataservice.data.model"})
public class DataserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataserviceApplication.class, args);
	}

}
