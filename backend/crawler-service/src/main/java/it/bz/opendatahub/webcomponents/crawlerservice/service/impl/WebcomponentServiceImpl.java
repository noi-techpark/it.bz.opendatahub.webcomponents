package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.NotFoundException;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentVersionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class WebcomponentServiceImpl implements WebcomponentService {
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String MANIFEST_FILE_NAME = "wcs-manifest.json";

    private final WebcomponentVersionService webcomponentVersionService;

    private final WebcomponentRepository webcomponentRepository;

    private final WebcomponentFactory webcomponentFactory;

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public WebcomponentServiceImpl(final WebcomponentVersionService webcomponentVersionService,
                                   final WebcomponentRepository webcomponentRepository,
                                   final WebcomponentFactory webcomponentFactory,
                                   final WorkspaceRepository workspaceRepository) {
        this.webcomponentVersionService = webcomponentVersionService;
        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentFactory = webcomponentFactory;
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public void updateWebcomponent(String uuid, String repoUrl) {
        Manifest manifest;
        try {
            manifest = getLatestVersionManifest(uuid);
        }
        catch (NotFoundException e) {
            return;
        }

        WebcomponentModel newEntry = webcomponentFactory.createFromManifest(uuid, manifest, repoUrl);

        WebcomponentModel entry;
        Optional<WebcomponentModel> probe = webcomponentRepository.findById(uuid);
        if(probe.isPresent()) {
            entry = probe.get();

            BeanUtils.copyProperties(newEntry, entry);
        }
        else {
            entry = newEntry;
        }

        webcomponentRepository.save(entry);
    }

    @Override
    public void markDeletedWhereOriginIsDeleted() {
        webcomponentRepository.markDeletedWhereOriginIsDeleted();
    }

    private Manifest getLatestVersionManifest(String uuid) {
        WebcomponentVersionModel version = webcomponentVersionService.getLatestVersionForWebcomponent(uuid);

        byte[] manifestData = workspaceRepository.readFile(Paths.get(uuid, version.getVersionTag(), MANIFEST_FILE_NAME));

        try {
            return objectMapper.readValue(manifestData, Manifest.class);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }
    }
}
