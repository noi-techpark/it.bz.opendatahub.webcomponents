package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadWebcomponentPort {
	Webcomponent getWebcomponentById(String uuid);

	List<Webcomponent> listAll();

	Page<Webcomponent> listPagedAndFiltered(Pageable pageRequest, ListWebcomponentUseCase.WebcomponentFilter filter);
}
