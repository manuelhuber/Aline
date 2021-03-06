package de.fh.rosenheim.aline.service;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.BookingException;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.BookingRepository;
import de.fh.rosenheim.aline.security.service.SecurityService;
import de.fh.rosenheim.aline.util.SeminarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static de.fh.rosenheim.aline.util.LoggingUtil.currentUser;

@Component
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BookingRepository bookingRepository;
    private final SecurityService securityService;
    private final SeminarService seminarService;
    private final UserService userService;

    public BookingService(BookingRepository bookingRepository, SecurityService securityService, SeminarService seminarService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.securityService = securityService;
        this.seminarService = seminarService;
        this.userService = userService;
    }

    /**
     * Tries to book the seminar to the user.
     * If the user already tried to book the seminar and it was DENIED, the old booking will be set to REQUESTED.
     * If the user (to whom the seminar is booked) is a TOP_DOG the booking will instantly be GRANTED.
     * If the current user (not necessarily the same as the one to whom the seminar is booked) is FRONT_OFFICE, the
     * booking will instantly be GRANTED.
     *
     * @throws BookingException If the given seminar ID or username are not valid
     *                          If the seminar is not bookable
     *                          If the seminar is already fully booked
     *                          If the seminar is already booked to the user with status "REQUESTED" or "GRANTED"
     */
    public Booking book(Long seminarId, String username) throws BookingException {
        try {
            Seminar seminar = seminarService.getSeminar(seminarId);

            if (!seminar.isBookable()) {
                throw new BookingException("This seminar is not bookable");
            }

            if (SeminarUtil.getActiveBookingCount(seminar) >= seminar.getMaximumParticipants()) {
                throw new BookingException("This seminar is already fully booked");
            }

            Booking booking = getValidBookingForUser(seminar, userService.getUserByName(username));
            bookingRepository.save(booking);
            log.info(currentUser() + "booked seminar with id=" + seminarId + " for user with username=" + username);
            return booking;
        } catch (NoObjectForIdException e) {
            throw new BookingException("The given ID for " + e.getObject().getSimpleName() + " was not valid: " + e.getId());
        }
    }

    /**
     * Returns the booking for the given ID
     *
     * @param id of the booking
     * @return Booking
     */
    public Booking getBooking(long id) throws NoObjectForIdException {
        Booking booking = bookingRepository.findOne(id);
        if (booking == null) {
            throw new NoObjectForIdException(Booking.class, id);
        }
        return booking;
    }

    public List<Booking> getBookingsForSeminar(long seminarId) {
        return bookingRepository.findBySeminarId(seminarId);
    }

    /**
     * Set the status of the booking to granted
     *
     * @param id of the booking
     * @return the updated booking
     * @throws AuthenticationException if the user is not allowed to grant the booking
     */
    public Booking grantBooking(long id) throws NoObjectForIdException, AuthenticationException {
        Booking booking = this.getBooking(id);
        if (securityService.canCurrentUserChangeBookingStatus(booking)) {
            booking.setStatus(BookingStatus.GRANTED);
            log.info(currentUser() + "granted booking with id=" + id + " successfully");
            return bookingRepository.save(booking);
        } else throw deny();
    }

    /**
     * Set the status of the booking to denied
     *
     * @param id of the booking
     * @return the updated booking
     * @throws AuthenticationException if the user is not allowed to deny the booking
     */
    public Booking denyBooking(long id) throws NoObjectForIdException, AuthenticationException {
        Booking booking = this.getBooking(id);
        if (securityService.canCurrentUserChangeBookingStatus(booking)) {
            booking.setStatus(BookingStatus.DENIED);
            log.info(currentUser() + "denied booking with id=" + id + " successfully");
            return bookingRepository.save(booking);
        } else throw deny();
    }

    /**
     * Delete the booking
     *
     * @param id of the booking
     */
    public void deleteBooking(long id) throws NoObjectForIdException {
        if (securityService.canCurrentUserDeleteBooking(getBooking(id))) {
            try {
                bookingRepository.delete(id);
                log.info(currentUser() + "deleted booking with id=" + id + " successfully");
            } catch (Exception e) {
                log.error(currentUser() + "tried to deleted booking with id=" + id + " but it failed.", e);
                throw e;
            }
        } else throw deny();
    }

    /**
     * Checks if the user has already booked the seminar.
     * If there is no booking it will create a new booking.
     * If there already is a DENIED booking, it will return the old booking but with status REQUESTED.
     * If there already is a REQUESTED or GRANTED booking it will throw an exception
     *
     * @return The booking with the appropriate status
     * @throws BookingException if there already exists a non-denied booking for this seminar/user combination
     */
    private Booking getValidBookingForUser(Seminar seminar, User user) throws BookingException {
        Booking booking;
        Optional<Booking> oldBooking = user.getBookings().stream()
                .filter(b -> b.getSeminar().equals(seminar)).findAny();

        if (oldBooking.isPresent()) {
            booking = oldBooking.get();
            // Reopen a denied booking
            if (booking.getStatus().equals(BookingStatus.DENIED)) {
                booking.setStatus(BookingStatus.REQUESTED);
                log.info(currentUser() + "reopened the request user with username=" + user.getUsername() + "to book seminar with id=" + seminar.getId());
            } else throw new BookingException(
                    "This booking already exists. Id=" + booking.getId() + " status=" + booking.getStatus().toString());
        } else {
            booking = new Booking();
            booking.setStatus(BookingStatus.REQUESTED);
            booking.setSeminar(seminar);
            booking.setUser(user);
        }

        // If the booking is for a TOP_DOG or made by the front office automatically grant the booking
        if (securityService.isTopDog(user.getUsername()) || securityService.isCurrentUserFrontOffice()) {
            booking.setStatus(BookingStatus.GRANTED);
        }

        return booking;
    }

    private AccessDeniedException deny() {
        return new AccessDeniedException("You don't have permission to edit this booking");
    }
}
