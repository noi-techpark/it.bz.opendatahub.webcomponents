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
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn(null);
    }

    @Test
    void isUpToDate_bothNull() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn(null);

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn(null);

        assertTrue(originService.isUpToDate());
    }

    @Test
    void isUpToDate_localNull() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn(null);

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn("asdfghjkl");

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_remoteNull() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn(null);

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_bothEmpty() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn("");

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn("");

        assertTrue(originService.isUpToDate());
    }

    @Test
    void isUpToDate_localEmpty() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn("");

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn("asdfghjkl");

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_remoteEmpty() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn("");

        assertFalse(originService.isUpToDate());
    }

    @Test
    void isUpToDate_sameString() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn("asdfghjkl");

        assertTrue(originService.isUpToDate());
    }
    @Test
    void isUpToDate_differentString() {
        when(
                systemRepository.getHeadCommitHashForOrigin()
        ).thenReturn("asdfghjkl");

        when(
                vcsApiRepository.getLatestCommitHash(anyString(), anyString())
        ).thenReturn("lkjhgfdsa");

        assertFalse(originService.isUpToDate());
    }
}