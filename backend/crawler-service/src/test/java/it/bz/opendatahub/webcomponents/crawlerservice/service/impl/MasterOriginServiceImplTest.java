package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.SystemRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MasterOriginServiceImplTest {
    @Mock
    private VcsApiRepository vcsApiRepository;

    @Mock
    private SystemRepository systemRepository;

    @Mock
    private OriginRepository originRepository;

    @InjectMocks
    private MasterOriginServiceImpl originService;

    @BeforeEach
    void initMocks() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn(null);
    }

    @Test
    void isUpToDate_bothNull() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn(null);

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn(null);

        assertTrue(originService.isUpToDate());
    }

    @Test
    void isUpToDate_localNull() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn(null);

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn("asdfghjkl");

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_remoteNull() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn(null);

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_bothEmpty() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn("");

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn("");

        assertTrue(originService.isUpToDate());
    }

    @Test
    void isUpToDate_localEmpty() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn("");

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn("asdfghjkl");

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_remoteEmpty() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn("");

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_sameString() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn("asdfghjkl");

        assertTrue(originService.isUpToDate());
    }
    @Test
    void isUpToDate_differentString() {
        when(
                systemRepository.getHeadOfMasterOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestRevisionHash(any(), anyString())
        ).thenReturn("lkjhgfdsa");

        assertFalse(originService.isUpToDate());
    }
}