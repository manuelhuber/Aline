package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${route.booking.base}")
public class BookingsController {

    private final BookingService bookingService;

    public BookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Get a single booking
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Booking getBookingById(@PathVariable long id) throws NoObjectForIdException {
        return bookingService.getBooking(id);
    }

    /**
     * Grant a booking
     */
    @RequestMapping(value = "/{id}/${route.booking.grant}", method = RequestMethod.POST)
    public Booking grantBooking(@PathVariable long id) throws NoObjectForIdException {
        return bookingService.grantBooking(id);
    }

    /**
     * Grant a booking
     */
    @RequestMapping(value = "/{id}/${route.booking.deny}", method = RequestMethod.POST)
    public Booking denyBooking(@PathVariable long id) throws NoObjectForIdException {
        return bookingService.denyBooking(id);
    }

    /**
     * Custom response if no Booking for the given id exists
     */
    @ExceptionHandler(NoObjectForIdException.class)
    public ResponseEntity<ErrorResponse> noObjectException(NoObjectForIdException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("No booking with id=" + ex.getId()),
                HttpStatus.NOT_FOUND);
    }
}
