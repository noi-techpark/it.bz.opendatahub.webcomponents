package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Origin;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.SystemRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.GitWorkspaceService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.OriginService;
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
public class OriginServiceImpl implements OriginService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private VcsApiRepository vcsApiRepository;
    private GitWorkspaceService gitWorkspaceService;
    private SystemRepository systemRepository;
    private OriginRepository originRepository;

    @Autowired
    public OriginServiceImpl(@Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository,
                             GitWorkspaceService gitWorkspaceService,
                             SystemRepository systemRepository,
                             OriginRepository originRepository) {

        this.vcsApiRepository = vcsApiRepository;
        this.gitWorkspaceService = gitWorkspaceService;
        this.systemRepository = systemRepository;
        this.originRepository = originRepository;
    }

    @Override
    public boolean isUpToDate() {
        String localHeadCommitHash = systemRepository.getHeadCommitHashForOrigin();

        String remoteHeadCommitHash = vcsApiRepository.getLatestCommitHash("https://github.com/uhufa/odh-web-components-store-demo-origins.git", "master");

        return Objects.equals(localHeadCommitHash, remoteHeadCommitHash);
    }

    @Override
    public void update() {
        gitWorkspaceService.checkoutHeadOfBranch("origin", "master");

        String remoteHeadCommitHash = vcsApiRepository.getLatestCommitHash("https://github.com/uhufa/odh-web-components-store-demo-origins.git", "master");
        systemRepository.setHeadCommitHashForOrigin(remoteHeadCommitHash);

        byte[] originsFileData = gitWorkspaceService.readFile("origin", "origins.json");

        processOriginsFile(originsFileData);
    }

    protected void processOriginsFile(byte[] originsFileData) {
        List<Origin> origins = readOriginsFile(originsFileData);

        for(Origin origin : origins) {
            processOrigin(origin);
        }
    }

    protected List<Origin> readOriginsFile(byte[] originsFileData) {
        try {
            return Arrays.asList(objectMapper.readValue(originsFileData, Origin[].class));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processOrigin(Origin origin) {
        log.info("origin: " + origin.getUrl());

        OriginModel originModel = new OriginModel();
        originModel.setUuid(origin.getUuid());
        originModel.setApi(origin.getApi());
        originModel.setUrl(origin.getUrl());

        originRepository.save(originModel);
    }
}
