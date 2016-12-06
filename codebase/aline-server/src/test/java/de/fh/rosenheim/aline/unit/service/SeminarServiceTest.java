package de.fh.rosenheim.aline.unit.service;

import de.fh.rosenheim.aline.model.domain.Category;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.SeminarBasics;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.exceptions.UnkownCategoryException;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.repository.CategoryRepository;
import de.fh.rosenheim.aline.repository.SeminarRepository;
import de.fh.rosenheim.aline.service.SeminarService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SeminarServiceTest {

    private SeminarRepository seminarRepository;
    private CategoryRepository categoryRepository;
    private SeminarService seminarService;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void createService() {
        seminarRepository = mock(SeminarRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        seminarService = new SeminarService(seminarRepository, categoryRepository);
    }

    @Before
    public void mockSecurityContext() {
        SecurityUser user = new SecurityUser("John", null, null, null, null, null);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void getNonExistingSeminar() throws NoObjectForIdException {
        given(seminarRepository.findOne((long) 1)).willReturn(null);
        exception.expect(NoObjectForIdException.class);
        seminarService.getSeminar(1);
    }

    @Test
    public void getSeminar() throws NoObjectForIdException {
        Seminar seminar = new Seminar();
        seminar.setName("foo");
        seminar.setDescription("bar");
        given(seminarRepository.findOne((long) 1)).willReturn(seminar);
        assertThat(seminarService.getSeminar(1)).isEqualTo(seminar);
    }

    @Test
    public void getAllSeminars() throws NoObjectForIdException {
        Seminar seminar = new Seminar();
        seminar.setName("foo");
        seminar.setDescription("bar");
        final Iterable<Seminar> list = new LinkedList<>(Collections.singletonList(seminar));
        given(seminarRepository.findAll()).willReturn(list);
        assertThat(seminarService.getAllSeminars()).isEqualTo(list);
    }

    @Test
    public void getPastSeminars() throws NoObjectForIdException, ParseException {
        Seminar onePastOneFutureDate = new Seminar();
        onePastOneFutureDate.setId((long) 1);
        onePastOneFutureDate.setDates(new Date[]{sdf.parse("10/08/2117"), sdf.parse("10/3/1899")});

        Seminar allPastDates = new Seminar();
        allPastDates.setId((long) 2);
        allPastDates.setDates(new Date[]{sdf.parse("10/08/1117"), sdf.parse("10/3/1899")});

        Seminar allFutureDates = new Seminar();
        allFutureDates.setId((long) 3);
        allFutureDates.setDates(new Date[]{sdf.parse("10/08/3117"), sdf.parse("10/3/3899")});

        Seminar noDates = new Seminar();
        noDates.setId((long) 4);

        final Iterable<Seminar> list
                = new LinkedList<>(Arrays.asList(onePastOneFutureDate, allPastDates, allFutureDates, noDates));
        given(seminarRepository.findAll()).willReturn(list);
        assertThat(seminarService.getPastSeminars())
                .contains(noDates)
                .contains(allPastDates)
                .doesNotContain(onePastOneFutureDate)
                .doesNotContain(allFutureDates);
    }

    @Test
    public void getCurrentSeminars() throws NoObjectForIdException, ParseException {
        Seminar onePastOneFutureDate = new Seminar();
        onePastOneFutureDate.setId((long) 1);
        onePastOneFutureDate.setDates(new Date[]{sdf.parse("10/08/2117"), sdf.parse("10/3/1899")});

        Seminar allPastDates = new Seminar();
        allPastDates.setId((long) 2);
        allPastDates.setDates(new Date[]{sdf.parse("10/08/1117"), sdf.parse("10/3/1899")});

        Seminar allFutureDates = new Seminar();
        allFutureDates.setId((long) 3);
        allFutureDates.setDates(new Date[]{sdf.parse("10/08/3117"), sdf.parse("10/3/3899")});

        Seminar noDates = new Seminar();
        noDates.setId((long) 4);

        final Iterable<Seminar> list
                = new LinkedList<>(Arrays.asList(onePastOneFutureDate, allPastDates, allFutureDates, noDates));
        given(seminarRepository.findAll()).willReturn(list);
        assertThat(seminarService.getCurrentSeminars())
                .contains(noDates)
                .contains(onePastOneFutureDate)
                .contains(allFutureDates)
                .doesNotContain(allPastDates);
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
    public void createNewSeminarWithInvalidCategory() throws UnkownCategoryException {
        given(categoryRepository.findAll()).willReturn(Lists.newArrayList(new Category("Hello World")));
        exception.expect(UnkownCategoryException.class);
        seminarService.createNewSeminar(new SeminarBasics());
        verify(seminarRepository, times(0)).save(any(Seminar.class));
    }

    @Test
    public void createNewSeminar() throws UnkownCategoryException {
        SeminarBasics newSeminar = new SeminarBasics();
        newSeminar.setName("foo");
        newSeminar.setDescription("bar");
        newSeminar.setCategory("Hello World");
        given(categoryRepository.findAll()).willReturn(Lists.newArrayList(new Category("Hello World")));

        seminarService.createNewSeminar(newSeminar);

        ArgumentCaptor<Seminar> argument = ArgumentCaptor.forClass(Seminar.class);
        verify(seminarRepository).save(argument.capture());
        assertEquals("foo", argument.getValue().getName());
        assertEquals("bar", argument.getValue().getDescription());
    }

    @Test
    public void updateNonExistingSeminar() throws NoObjectForIdException, UnkownCategoryException {
        SeminarBasics seminarUpdate = new SeminarBasics();
        seminarUpdate.setName("foo");
        seminarUpdate.setDescription("bar");

        given(seminarRepository.save(any(Seminar.class))).willReturn(null);
        exception.expect(NoObjectForIdException.class);
        seminarService.updateSeminar(1, seminarUpdate);
    }

    @Test
    public void updateSeminarWithInvalidCategory() throws NoObjectForIdException, UnkownCategoryException {

        Seminar actualSeminar = new Seminar();
        actualSeminar.setId((long) 10);
        actualSeminar.setName("hello");
        actualSeminar.setDescription("world");

        given(seminarRepository.findOne((long) 10)).willReturn(actualSeminar);
        given(categoryRepository.findAll()).willReturn(Lists.newArrayList(new Category("Hello World")));

        exception.expect(UnkownCategoryException.class);
        seminarService.updateSeminar(10, new SeminarBasics());
        verify(seminarRepository, times(0)).save(any(Seminar.class));
    }

    @Test
    public void updateSeminar() throws NoObjectForIdException, UnkownCategoryException {
        SeminarBasics seminarUpdate = new SeminarBasics();
        seminarUpdate.setName("foo");
        seminarUpdate.setDescription("bar");
        seminarUpdate.setCategory("Hello World");

        Seminar actualSeminar = new Seminar();
        actualSeminar.setId((long) 10);
        actualSeminar.setName("hello");
        actualSeminar.setDescription("world");

        given(seminarRepository.findOne((long) 10)).willReturn(actualSeminar);
        given(seminarRepository.save(actualSeminar)).willReturn(actualSeminar);
        given(categoryRepository.findAll()).willReturn(Lists.newArrayList(new Category("Hello World")));

        Seminar returnValue = seminarService.updateSeminar(10, seminarUpdate);

        assertEquals(returnValue, actualSeminar);
        ArgumentCaptor<Seminar> argument = ArgumentCaptor.forClass(Seminar.class);
        verify(seminarRepository).save(argument.capture());
        assertEquals("foo", argument.getValue().getName());
        assertEquals("bar", argument.getValue().getDescription());
    }
}
