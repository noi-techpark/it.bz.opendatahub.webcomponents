package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import java.util.List;

public interface ListSearchtagsUseCase {
	List<String> listAllUsedSearchtags();

	List<String> listAllSearchtags();
}
