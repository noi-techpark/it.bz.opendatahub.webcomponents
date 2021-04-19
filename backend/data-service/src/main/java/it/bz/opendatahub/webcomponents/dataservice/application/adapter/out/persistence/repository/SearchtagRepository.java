package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository;

import java.util.List;

public interface SearchtagRepository {
    List<String> listAllUsedSearchtags();

    List<String> listAllSearchtags();
}
