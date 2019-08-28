package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.crawlerservice.config.MasterOriginConfiguration;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Origin;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.OriginModelFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.SystemRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.MasterOriginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MasterOriginServiceImpl implements MasterOriginService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String ORIGINS_FILE_NAME = "origins.json";

    private MasterOriginConfiguration masterOriginConfiguration;

    private VcsApiRepository vcsApiRepository;
    private SystemRepository systemRepository;
    private OriginRepository originRepository;

    private OriginModelFactory originModelFactory;

    private final GitRemote gitRemote;

    @Autowired
    public MasterOriginServiceImpl(MasterOriginConfiguration masterOriginConfiguration,
                                   @Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository,
                                   SystemRepository systemRepository,
                                   OriginRepository originRepository,
                                   OriginModelFactory originModelFactory) {

        this.masterOriginConfiguration = masterOriginConfiguration;
        this.vcsApiRepository = vcsApiRepository;
        this.systemRepository = systemRepository;
        this.originRepository = originRepository;

        this.originModelFactory = originModelFactory;

        this.gitRemote =  GitRemote.of(masterOriginConfiguration.getUrl(), "github");
    }

    @Override
    public boolean isUpToDate() {
        String localHeadCommitHash = systemRepository.getHeadOfMasterOrigin();
        String remoteHeadCommitHash = getRemoteHeadRevision();

        return Objects.equals(localHeadCommitHash, remoteHeadCommitHash);
    }

    @Override
    public void update() {
        String headRevision = getRemoteHeadRevision();

        List<Origin> origins = readOriginsFromMaster(headRevision);

        processOrigins(origins);

        markAllNoLongerInListAsDeleted(origins);

        systemRepository.setHeadOfMasterOrigin(headRevision);
    }

    private void markAllNoLongerInListAsDeleted(List<Origin> origins) {
        if(!origins.isEmpty()) {
            List<String> existingIds = new ArrayList<>();
            for(Origin origin : origins) {
                existingIds.add(origin.getUuid());
            }

            originRepository.markAllNotInListAsDeleted(existingIds);
        }
    }

    private List<Origin> readOriginsFromMaster(String headRevision) {
        ByteArrayOutputStream originsFileData = vcsApiRepository.getFileContents(gitRemote, headRevision, ORIGINS_FILE_NAME);

        return readOriginsFile(originsFileData);
    }

    private String getRemoteHeadRevision() {
        return vcsApiRepository.getLatestRevisionHash(
                gitRemote,
                masterOriginConfiguration.getBranch()
        );
    }

    private List<Origin> readOriginsFile(ByteArrayOutputStream originsFileData) {
        try {
            return Arrays.asList(objectMapper.readValue(originsFileData.toByteArray(), Origin[].class));
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }
    }

    private void processOrigins(List<Origin> origins) {
        for(Origin origin : origins) {
            log.info("processing origin: {}", origin.getUrl());
            processOrigin(origin);
        }
    }

    private void processOrigin(Origin origin) {
        OriginModel originModel = originModelFactory.create(origin);

        originRepository.save(originModel);
    }
}
