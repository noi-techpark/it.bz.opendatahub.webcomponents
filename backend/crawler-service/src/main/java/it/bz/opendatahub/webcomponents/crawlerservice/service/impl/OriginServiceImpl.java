package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginTagModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.id.OriginTagId;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginTagRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.OriginService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OriginServiceImpl implements OriginService {
    private final WebcomponentService webcomponentService;
    private final WebcomponentVersionService webcomponentVersionService;

    private final OriginRepository originRepository;
    private final OriginTagRepository originTagRepository;

    private final VcsApiRepository vcsApiRepository;

    @Autowired
    public OriginServiceImpl(final WebcomponentService webcomponentService,
                             final WebcomponentVersionService webcomponentVersionService,
                             final OriginRepository originRepository,
                             final OriginTagRepository originTagRepository,
                             @Qualifier("githubApiRepository") final VcsApiRepository vcsApiRepository) {

        this.webcomponentService = webcomponentService;
        this.webcomponentVersionService = webcomponentVersionService;
        this.originRepository = originRepository;
        this.originTagRepository = originTagRepository;
        this.vcsApiRepository = vcsApiRepository;
    }

    @Override
    public List<OriginModel> listAllOrigins() {
        return originRepository.findAll();
    }

    @Override
    public List<OriginModel> listAllOrigins(boolean deleted) {
        return originRepository.findAllByDeletedFlag(deleted);
    }

    @Override
    public void updateOrigin(OriginModel origin) {
        updateOriginTags(origin);

        updateMainEntry(origin);
    }

    private void updateMainEntry(OriginModel origin) {
        webcomponentService.updateWebcomponent(origin.getUuid(), origin.getUrl());
    }

    private void updateOriginTags(OriginModel origin) {
        GitRemote gitRemote = GitRemote.of(origin);

        List<TagEntry> tagEntries = vcsApiRepository.listVersionTags(gitRemote);

        for(TagEntry tag : tagEntries) {
            Optional<OriginTagModel> probe = originTagRepository.findById(OriginTagId.of(origin.getUuid(), tag.getName()));

            if(probe.isPresent()) {
                OriginTagModel entry = probe.get();

                if(!entry.getHash().equals(tag.getRevisionHash())) {
                    updateTaggedVersion(origin, tag);
                }
            }
            else {
                createTaggedVersion(origin, tag);
            }
        }

        markAllTagsNotInListAsDeleted(origin, tagEntries);
    }

    private void updateTaggedVersion(OriginModel origin, TagEntry tagEntry) {
        webcomponentVersionService.purgeVersionForTag(origin, tagEntry);

        createTaggedVersion(origin, tagEntry);
    }

    private void createTaggedVersion(OriginModel origin, TagEntry tagEntry) {
        webcomponentVersionService.createVersionFromTag(origin, tagEntry);
    }

    private void markAllTagsNotInListAsDeleted(OriginModel origin, List<TagEntry> tags) {
        List<String> tagNames = new ArrayList<>();
        for(TagEntry tag : tags) {
            tagNames.add(tag.getName());
        }

        if(!tagNames.isEmpty()) {
            originTagRepository.markAllTagsNotInListAsDeleted(origin.getUuid(), tagNames);
        }

        webcomponentVersionService.cascadeDeletedTags();
    }

    @Override
    public void cascadeDeletedOrigins() {
        webcomponentService.markDeletedWhereOriginIsDeleted();
    }
}
