package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import java.util.List;

public interface ReadSearchtagsPort {
	List<String> listAllUsedSearchtags();

	List<String> listAllSearchtags();
}
