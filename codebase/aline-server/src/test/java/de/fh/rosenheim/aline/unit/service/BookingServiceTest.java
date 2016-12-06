package de.fh.rosenheim.aline.unit.service;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.BookingException;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.BookingRepository;
import de.fh.rosenheim.aline.security.service.SecurityService;
import de.fh.rosenheim.aline.service.BookingService;
import de.fh.rosenheim.aline.service.SeminarService;
import de.fh.rosenheim.aline.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BookingServiceTest {

    private BookingRepository bookingRepository;
    private SecurityService securityService;
    private SeminarService seminarService;
    private UserService userService;
    private BookingService bookingService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void createService() {
        bookingRepository = mock(BookingRepository.class);
        securityService = mock(SecurityService.class);
        seminarService = mock(SeminarService.class);
        userService = mock(UserService.class);
        bookingService = new BookingService(bookingRepository, securityService, seminarService, userService);
    }

    @Test
    public void retrieveNonExistingBooking() throws NoObjectForIdException {
        given(bookingRepository.findOne((long) 1)).willReturn(null);
        exception.expect(NoObjectForIdException.class);
        bookingService.getBooking(1);
    }

    @Test
    public void retrievingBooking() throws NoObjectForIdException {
        Booking booking = new Booking((long) 9, null, null, BookingStatus.DENIED, null, null);
        given(bookingRepository.findOne((long) 9)).willReturn(booking);
        assertThat(bookingService.getBooking((long) 9)).isEqualTo(booking);
    }

    @Test
    public void bookNotBookableSeminar() throws NoObjectForIdException, BookingException {
        exception.expect(BookingException.class);

        Seminar seminar = new Seminar();
        seminar.setBookable(false);
        given(seminarService.getSeminar(1)).willReturn(seminar);

        bookingService.book((long) 1, "Foo");
    }

    @Test
    public void bookNonExistingSeminar() throws NoObjectForIdException, BookingException {
        exception.expect(BookingException.class);

        given(seminarService.getSeminar(1)).willThrow(new NoObjectForIdException(Seminar.class, 1));

        bookingService.book((long) 1, "Foo");
    }

    @Test
    public void bookForNonExistingUser() throws NoObjectForIdException, BookingException {
        exception.expect(BookingException.class);

        Seminar seminar = new Seminar();
        seminar.setBookable(true);
        given(seminarService.getSeminar(1)).willReturn(seminar);
        given(userService.getUserByName("Foo")).willThrow(new NoObjectForIdException(User.class, "Foo"));

        bookingService.book((long) 1, "Foo");
    }
}
