package de.fh.rosenheim.aline.unit.service;

import de.fh.rosenheim.aline.domain.entity.Seminar;
import de.fh.rosenheim.aline.domain.entity.User;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.SeminarRepository;
import de.fh.rosenheim.aline.service.SeminarService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SeminarServiceTest {

    private SeminarRepository seminarRepository;
    private SeminarService seminarService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void createService() {
        seminarRepository = mock(SeminarRepository.class);
        seminarService = new SeminarService(seminarRepository);
    }

    @Before
    public void mockSecurityContext() {
        User user = new User();
        user.setUsername("John");

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void seminarNotFound() throws NoObjectForIdException {
        given(seminarRepository.findOne((long) 1)).willReturn(null);
        exception.expect(NoObjectForIdException.class);
        seminarService.getSeminar(1);
    }

    @Test
    public void getSeminar() throws NoObjectForIdException {
        Seminar seminar = Seminar.builder().name("foo").description("bar").build();
        given(seminarRepository.findOne((long) 1)).willReturn(seminar);
        assertThat(seminarService.getSeminar(1)).isEqualTo(seminar);
    }

    @Test
    public void getAllSeminars() throws NoObjectForIdException {
        Seminar seminar = Seminar.builder().name("foo").description("bar").build();
        final Iterable<Seminar> list = new LinkedList<>(Arrays.asList(seminar));
        given(seminarRepository.findAll()).willReturn(list);
        assertThat(seminarService.getAllSeminars()).isEqualTo(list);
    }

    @Test
    public void deleteNonExistingSeminar() throws NoObjectForIdException {
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(seminarRepository).delete((long) 1);
        exception.expect(NoObjectForIdException.class);
        seminarService.deleteSeminar(1);
    }

    @Test
    public void deleteSeminarWithException() throws NoObjectForIdException {
        Mockito.doThrow(new RuntimeException("#sadface")).when(seminarRepository).delete((long) 1);
        exception.expect(RuntimeException.class);
        seminarService.deleteSeminar(1);
    }

    @Test
    public void deleteSeminar() throws NoObjectForIdException {
        seminarService.deleteSeminar(1);
        verify(seminarRepository).delete((long) 1);
    }

    @Test
    public void createNewSeminar() {
        Seminar newSeminar = Seminar.builder().id((long) 5).name("foo").description("bar").build();
        Seminar actualSeminar = Seminar.builder().id((long) 10).name("foo").description("bar").build();
        given(seminarRepository.save(newSeminar)).willReturn(actualSeminar);

        seminarService.createNewSeminar(newSeminar);
        assertThat(newSeminar.getId()).isEqualTo(null);
        verify(seminarRepository).save(newSeminar);
    }
}
