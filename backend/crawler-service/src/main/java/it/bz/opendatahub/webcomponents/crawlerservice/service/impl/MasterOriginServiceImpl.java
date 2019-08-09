package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.crawlerservice.config.MasterOriginConfiguration;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Origin;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.SystemRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.MasterOriginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MasterOriginServiceImpl implements MasterOriginService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private MasterOriginConfiguration masterOriginConfiguration;

    private VcsApiRepository vcsApiRepository;
    private SystemRepository systemRepository;
    private OriginRepository originRepository;

    @Autowired
    public MasterOriginServiceImpl(MasterOriginConfiguration masterOriginConfiguration,
                                   @Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository,
                                   SystemRepository systemRepository,
                                   OriginRepository originRepository) {

        this.masterOriginConfiguration = masterOriginConfiguration;
        this.vcsApiRepository = vcsApiRepository;
        this.systemRepository = systemRepository;
        this.originRepository = originRepository;
    }

    @Override
    public boolean isUpToDate() {
        String localHeadCommitHash = systemRepository.getHeadCommitHashForOrigin();
        String remoteHeadCommitHash = getRemoteHeadCommitHash();

        return Objects.equals(localHeadCommitHash, remoteHeadCommitHash);
    }

    @Override
    public void update() {
        String remoteHeadCommitHash = getRemoteHeadCommitHash();

        byte [] originsFileData = vcsApiRepository.getFileContentsForCommitHash(masterOriginConfiguration.getUrl(), remoteHeadCommitHash, "origins.json");

        systemRepository.setHeadCommitHashForOrigin(remoteHeadCommitHash);

        processOriginsFile(originsFileData);
    }

    private String getRemoteHeadCommitHash() {
        return vcsApiRepository.getLatestCommitHash(
                masterOriginConfiguration.getUrl(),
                masterOriginConfiguration.getBranch()
        );
    }

    private void processOriginsFile(byte[] originsFileData) {
        List<Origin> origins = readOriginsFile(originsFileData);

        for(Origin origin : origins) {
            processOrigin(origin);
        }
    }

    private List<Origin> readOriginsFile(byte[] originsFileData) {
        try {
            return Arrays.asList(objectMapper.readValue(originsFileData, Origin[].class));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processOrigin(Origin origin) {
        log.info("origin: " + origin.getUrl());

        OriginModel originModel = new OriginModel();
        originModel.setUuid(origin.getUuid());
        originModel.setApi(origin.getApi());
        originModel.setUrl(origin.getUrl());

        originRepository.save(originModel);
    }
}
