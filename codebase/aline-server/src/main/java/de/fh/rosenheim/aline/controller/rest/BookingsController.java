package de.fh.rosenheim.aline.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.dtos.booking.BookingDTO;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.booking.BookingRequestDTO;
import de.fh.rosenheim.aline.model.dtos.generic.ErrorResponse;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.model.exceptions.BookingException;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.service.BookingService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
     * @param httpServletRequest needed to access the X-Auth-Token
     * @return The booking
     * @throws NoObjectForIdException if ID or username are not valid
     * @throws BookingException       if the booking could not be created (reasons in the message)
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("@securityService.canBookForUser(principal, #bookingRequest.getUserName())")
    @ApiOperation(value = "book seminar", notes = "Books the seminar with the given ID to the current user (detected via token) or the given name if the current user has sufficient permission")
    public BookingDTO book(HttpServletRequest httpServletRequest, @Validated @RequestBody BookingRequestDTO bookingRequest)
            throws BookingException {
        String requestName = bookingRequest.getUserName();
        String name = requestName != null && requestName.length() > 0
                ? requestName
                : controllerUtil.getUsername(httpServletRequest);
        return BookingFactory.toBookingDTO(bookingService.book(bookingRequest.getSeminarId(), name));
    }

    /**
     * Get a single booking
     *
     * @param id of the booking
     * @return the booking
     * @throws NoObjectForIdException if there is no Booking for the given ID
     */
    @JsonView(View.BookingOverview.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BookingDTO getBookingById(@PathVariable long id) throws NoObjectForIdException {
        return BookingFactory.toBookingDTO(bookingService.getBooking(id));
    }

    /**
     * Set the booking status to GRANTED
     *
     * @param id of the booking
     * @return The updated booking
     * @throws NoObjectForIdException if there is no Booking for the given ID
     */
    @RequestMapping(value = "/{id}/${route.booking.grant}", method = RequestMethod.POST)
    public BookingDTO grantBooking(@PathVariable long id) throws NoObjectForIdException {
        return BookingFactory.toBookingDTO(bookingService.grantBooking(id));
    }

    /**
     * Set the booking status to DENIED
     *
     * @param id of the booking
     * @return The updated booking
     * @throws NoObjectForIdException if there is no Booking for the given ID
     */
    @RequestMapping(value = "/{id}/${route.booking.deny}", method = RequestMethod.POST)
    public BookingDTO denyBooking(@PathVariable long id) throws NoObjectForIdException {
        return BookingFactory.toBookingDTO(bookingService.denyBooking(id));
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
