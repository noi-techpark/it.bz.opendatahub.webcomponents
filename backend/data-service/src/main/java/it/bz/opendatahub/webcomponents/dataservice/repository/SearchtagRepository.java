package it.bz.opendatahub.webcomponents.dataservice.repository;

import java.util.List;
import java.util.Optional;

public interface SearchtagRepository {
    List<String> listAllUsedSearchtags();

    List<String> listAllSearchtags();

    Optional<String> findOneByName(String name);
}
