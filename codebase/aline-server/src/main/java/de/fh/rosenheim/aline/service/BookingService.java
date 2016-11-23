package de.fh.rosenheim.aline.service;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.BookingRepository;
import de.fh.rosenheim.aline.security.service.SecurityService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SecurityService securityService;

    public BookingService(BookingRepository bookingRepository, SecurityService securityService) {
        this.bookingRepository = bookingRepository;
        this.securityService = securityService;
    }

    public Booking getBooking(long id) throws NoObjectForIdException {
        Booking booking = bookingRepository.findOne(id);
        if (booking == null) {
            throw new NoObjectForIdException(id);
        }
        return booking;
    }

    public Booking grantBooking(long id) throws NoObjectForIdException, AuthenticationException {
        Booking booking = this.getBooking(id);
        if (securityService.canCurrentUserChangeBookingStatus(booking)) {
            booking.setStatus(BookingStatus.GRANTED);
            return bookingRepository.save(booking);
        } else throw deny();
    }

    public Booking denyBooking(long id) throws NoObjectForIdException, AuthenticationException {
        Booking booking = this.getBooking(id);
        if (securityService.canCurrentUserChangeBookingStatus(booking)) {
            booking.setStatus(BookingStatus.DENIED);
            return bookingRepository.save(booking);
        } else throw deny();
    }

    private AccessDeniedException deny() {
        return new AccessDeniedException("You can't change booking status for this user");
    }
}
