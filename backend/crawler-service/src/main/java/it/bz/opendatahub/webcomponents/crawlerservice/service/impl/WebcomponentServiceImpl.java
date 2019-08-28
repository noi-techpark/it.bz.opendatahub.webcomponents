package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.CommitEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRevision;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.NotFoundException;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentVersionFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WebcomponentServiceImpl implements WebcomponentService {
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String MANIFEST_FILE_NAME = "wcs-manifest.json";

    private WebcomponentVersionService webcomponentVersionService;

    private WebcomponentRepository webcomponentRepository;

    private WebcomponentFactory webcomponentFactory;

    private WorkspaceRepository workspaceRepository;

    @Autowired
    public WebcomponentServiceImpl(WebcomponentVersionService webcomponentVersionService,
                                   WebcomponentRepository webcomponentRepository,
                                   WebcomponentFactory webcomponentFactory,
                                   WorkspaceRepository workspaceRepository) {
        this.webcomponentVersionService = webcomponentVersionService;
        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentFactory = webcomponentFactory;
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public void updateWebcomponent(String uuid) {
        Manifest manifest;
        try {
            manifest = getLatestVersionManifest(uuid);
        }
        catch (NotFoundException e) {
            return;
        }

        WebcomponentModel newEntry = webcomponentFactory.createFromManifest(uuid, manifest);

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
