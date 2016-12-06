package de.fh.rosenheim.aline.unit.service;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.BookingException;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.repository.BookingRepository;
import de.fh.rosenheim.aline.security.service.SecurityService;
import de.fh.rosenheim.aline.service.BookingService;
import de.fh.rosenheim.aline.service.SeminarService;
import de.fh.rosenheim.aline.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BookingServiceTest {

    private static final String USERNAME = "John";
    private SecurityUser securityUser;
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

    @Before
    public void mockSecurityContext() {
        securityUser = new SecurityUser(USERNAME, null, null, null, null, null);

        Authentication auth = new UsernamePasswordAuthenticationToken(securityUser, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
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

    @Test
    public void bookTwicePreviousStatusRequested() throws NoObjectForIdException, BookingException {
        exception.expect(BookingException.class);

        Seminar seminar = new Seminar();
        seminar.setBookable(true);
        given(seminarService.getSeminar(1)).willReturn(seminar);

        User user = new User();
        user.setUsername(USERNAME);
        user.addBooking(Booking.builder().user(user).seminar(seminar).status(BookingStatus.REQUESTED).build());
        given(userService.getUserByName(USERNAME)).willReturn(user);

        bookingService.book((long) 1, USERNAME);
    }

    @Test
    public void bookTwicePreviousStatusDenied() throws NoObjectForIdException, BookingException {
        Seminar seminar = new Seminar();
        seminar.setBookable(true);
        given(seminarService.getSeminar(1)).willReturn(seminar);

        User user = new User();
        user.setUsername(USERNAME);
        Booking previousBooking = Booking.builder().user(user).seminar(seminar).status(BookingStatus.DENIED).build();
        user.addBooking(previousBooking);
        given(userService.getUserByName(USERNAME)).willReturn(user);

        assertThat(bookingService.book((long) 1, USERNAME)).isEqualTo(previousBooking);
        assertThat(previousBooking.getStatus()).isEqualTo(BookingStatus.REQUESTED);
    }

    @Test
    public void bookingSeminar() throws NoObjectForIdException, BookingException {
        Seminar seminar = new Seminar();
        seminar.setBookable(true);
        given(seminarService.getSeminar(1)).willReturn(seminar);

        User user = new User();
        user.setUsername(USERNAME);
        given(userService.getUserByName(USERNAME)).willReturn(user);

        Booking newBooking = bookingService.book((long) 1, USERNAME);
        assertThat(newBooking.getStatus()).isEqualTo(BookingStatus.REQUESTED);
        assertThat(newBooking.getSeminar()).isEqualTo(seminar);
        assertThat(newBooking.getUser()).isEqualTo(user);
    }

    @Test
    public void bookingSeminarAsFrontOffice() throws NoObjectForIdException, BookingException {
        Seminar seminar = new Seminar();
        seminar.setBookable(true);
        given(seminarService.getSeminar(1)).willReturn(seminar);

        User user = new User();
        user.setUsername(USERNAME);
        given(userService.getUserByName(USERNAME)).willReturn(user);

        given(securityService.isCurrentUserFrontOffice()).willReturn(true);

        Booking newBooking = bookingService.book((long) 1, USERNAME);
        assertThat(newBooking.getStatus()).isEqualTo(BookingStatus.GRANTED);
        assertThat(newBooking.getSeminar()).isEqualTo(seminar);
        assertThat(newBooking.getUser()).isEqualTo(user);
    }

    @Test
    public void bookingSeminarForTopDog() throws NoObjectForIdException, BookingException {
        Seminar seminar = new Seminar();
        seminar.setBookable(true);
        given(seminarService.getSeminar(1)).willReturn(seminar);

        User user = new User();
        user.setUsername(USERNAME);
        given(userService.getUserByName(USERNAME)).willReturn(user);

        given(securityService.isTopDog(USERNAME)).willReturn(true);

        Booking newBooking = bookingService.book((long) 1, USERNAME);
        assertThat(newBooking.getStatus()).isEqualTo(BookingStatus.GRANTED);
        assertThat(newBooking.getSeminar()).isEqualTo(seminar);
        assertThat(newBooking.getUser()).isEqualTo(user);
    }

    @Test
    public void deleteBookingWithInsufficientPermission() throws NoObjectForIdException {
        exception.expect(AccessDeniedException.class);
        Seminar seminar = new Seminar();
        seminar.setBookable(true);

        User user = new User();
        user.setUsername(USERNAME);

        Booking booking = Booking.builder().user(user).seminar(seminar).status(BookingStatus.DENIED).build();
        given(bookingRepository.findOne((long) 1)).willReturn(booking);
        given(securityService.canCurrentUserDeleteBooking(booking)).willReturn(false);
        bookingService.deleteBooking(1);
    }

    @Test
    public void deleteBooking() throws NoObjectForIdException {
        Seminar seminar = new Seminar();
        seminar.setBookable(true);

        User user = new User();
        user.setUsername(USERNAME);

        Booking booking = Booking.builder().user(user).seminar(seminar).status(BookingStatus.DENIED).build();
        given(bookingRepository.findOne((long) 1)).willReturn(booking);
        given(securityService.canCurrentUserDeleteBooking(booking)).willReturn(true);
        bookingService.deleteBooking(1);
    }

    @Test
    public void grantBookingWithInsufficientPermission() throws NoObjectForIdException {
        exception.expect(AccessDeniedException.class);

        Seminar seminar = new Seminar();
        seminar.setBookable(true);

        User user = new User();
        user.setUsername(USERNAME);

        Booking booking = Booking.builder().user(user).seminar(seminar).status(BookingStatus.REQUESTED).build();
        given(bookingRepository.findOne((long) 1)).willReturn(booking);
        given(securityService.canCurrentUserChangeBookingStatus(booking)).willReturn(false);
        bookingService.grantBooking(1);
    }

    @Test
    public void grantBooking() throws NoObjectForIdException {
        Seminar seminar = new Seminar();
        seminar.setBookable(true);

        User user = new User();
        user.setUsername(USERNAME);

        Booking booking = Booking.builder().user(user).seminar(seminar).status(BookingStatus.REQUESTED).build();
        given(bookingRepository.findOne((long) 1)).willReturn(booking);
        given(securityService.canCurrentUserChangeBookingStatus(booking)).willReturn(true);
        given(bookingRepository.save(booking)).willReturn(booking);

        assertThat(bookingService.grantBooking(1).getStatus()).isEqualTo(BookingStatus.GRANTED);
    }

    @Test
    public void denyBooking() throws NoObjectForIdException {
        Seminar seminar = new Seminar();
        seminar.setBookable(true);

        User user = new User();
        user.setUsername(USERNAME);

        Booking booking = Booking.builder().user(user).seminar(seminar).status(BookingStatus.REQUESTED).build();
        given(bookingRepository.findOne((long) 1)).willReturn(booking);
        given(securityService.canCurrentUserChangeBookingStatus(booking)).willReturn(true);
        given(bookingRepository.save(booking)).willReturn(booking);

        assertThat(bookingService.denyBooking(1).getStatus()).isEqualTo(BookingStatus.DENIED);
    }
}
