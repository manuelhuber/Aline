package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.controller.util.SwaggerTexts;
import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.exceptions.BookingException;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.service.BookingService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * All HTTP endpoints related to booking
 */
@RestController
@RequestMapping("${route.booking.base}")
public class BookingsController {

    private final BookingService bookingService;
    private final ControllerUtil controllerUtil;

    public BookingsController(BookingService bookingService, ControllerUtil controllerUtil) {
        this.bookingService = bookingService;
        this.controllerUtil = controllerUtil;
    }

    // ------------------------------------------------------------------------------------------------- Booking Handler

    /**
     * Books the given user or if no name is given the owner of the X-Auth-Token to the given Seminar
     *
     * @param seminarId The ID of the seminar
     * @param queryName the username of the person who wants to attend the seminar
     * @param request   needed to access the X-Auth-Token
     * @return The booking
     * @throws NoObjectForIdException if ID or username are not valid
     * @throws BookingException       if the booking could not be created (reasons in the message)
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("@securityService.canBookForUser(principal, #queryName)")
    @ApiOperation(value = "book seminar", notes = "Books the seminar with the given ID to the current user (detected via token) or the given name if the current user has sufficient permission")
    public Booking book(
            @ApiParam(value = "seminarId") @RequestParam(name = "seminarId") long seminarId,
            @ApiParam(value = SwaggerTexts.OTHER_USER) @RequestParam(required = false, name = "name") String queryName,
            HttpServletRequest request) throws NoObjectForIdException, BookingException {
        String name = queryName != null ? queryName : controllerUtil.getUsername(request);
        return bookingService.book(seminarId, name);
    }

    /**
     * Get a single booking
     *
     * @param id of the booking
     * @return the booking
     * @throws NoObjectForIdException if there is no Booking for the given ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Booking getBookingById(@PathVariable long id) throws NoObjectForIdException {
        return bookingService.getBooking(id);
    }

    /**
     * Set the booking status to GRANTED
     *
     * @param id of the booking
     * @return The updated booking
     * @throws NoObjectForIdException if there is no Booking for the given ID
     */
    @RequestMapping(value = "/{id}/${route.booking.grant}", method = RequestMethod.POST)
    public Booking grantBooking(@PathVariable long id) throws NoObjectForIdException {
        return bookingService.grantBooking(id);
    }

    /**
     * Set the booking status to DENIED
     *
     * @param id of the booking
     * @return The updated booking
     * @throws NoObjectForIdException if there is no Booking for the given ID
     */
    @RequestMapping(value = "/{id}/${route.booking.deny}", method = RequestMethod.POST)
    public Booking denyBooking(@PathVariable long id) throws NoObjectForIdException {
        return bookingService.denyBooking(id);
    }

    /**
     * Delete the booking
     *
     * @param id of the booking
     * @return
     * @throws NoObjectForIdException if there is no Booking for the given ID
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBookingById(@PathVariable long id) throws NoObjectForIdException {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------------------------------------------------------------- Exception Handler

    /**
     * Custom response if no Booking for the given id exists
     */
    @ExceptionHandler(NoObjectForIdException.class)
    public ResponseEntity<ErrorResponse> noObjectException(NoObjectForIdException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("No " + ex.getObject().getName() + " with id='" + ex.getId() + "'"),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Response for issues with the booking of a seminar
     */
    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ErrorResponse> bookingException(BookingException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
