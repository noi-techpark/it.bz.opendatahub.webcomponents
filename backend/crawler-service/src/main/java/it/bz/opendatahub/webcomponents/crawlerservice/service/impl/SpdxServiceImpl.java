package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.spdx.Licenses;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.SpdxRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.SpdxService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SpdxServiceImpl implements SpdxService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private SpdxRepository spdxRepository;

    private VcsApiRepository vcsApiRepository;

    @Autowired
    public SpdxServiceImpl(SpdxRepository spdxRepository,
                           @Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository) {
        this.spdxRepository = spdxRepository;
        this.vcsApiRepository = vcsApiRepository;
    }

    @Override
    public void update() {
        GitRemote gitRemote = GitRemote.of("https://github.com/spdx/license-list-data.git", "github");

        List<TagEntry> tags = vcsApiRepository.listVersionTags(gitRemote);

        tags.sort(Comparator.comparing(TagEntry::getName));

        ByteArrayOutputStream data = vcsApiRepository.getFileContents(gitRemote, tags.get(0).getRevisionHash(), "json/licenses.json");

        Licenses licenses;
        try {
            licenses = objectMapper.readValue(data.toByteArray(), Licenses.class);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }

        spdxRepository.deleteAll();

        List<SpdxLicenseModel> entries = new ArrayList<>();
        for(Licenses.License license : licenses.getLicenses()) {
            SpdxLicenseModel newEntry = new SpdxLicenseModel();

            BeanUtils.copyProperties(license, newEntry);

            entries.add(newEntry);
        }

        spdxRepository.saveAll(entries);
    }
}
