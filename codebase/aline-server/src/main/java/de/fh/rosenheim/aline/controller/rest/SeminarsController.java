package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.SeminarBasics;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.service.SeminarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${route.seminar.base}")
public class SeminarsController {

    private final SeminarService seminarService;

    public SeminarsController(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    /**
     * Get all seminars
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Seminar> getAllSeminars() {
        return seminarService.getAllSeminars();
    }

    /**
     * Add a new seminar
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public Seminar addSeminar(@RequestBody SeminarBasics seminar) {
        return seminarService.createNewSeminar(seminar);
    }

    /**
     * Get a single seminar
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Seminar getSeminarById(@PathVariable long id) throws NoObjectForIdException {
        return seminarService.getSeminar(id);
    }

    /**
     * Update basic info of a seminar (no ID, creation date, update date or bookings)
     * Properties that are not set will be set to null/0
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public Seminar updateSeminar(@PathVariable long id, @RequestBody SeminarBasics seminar) throws NoObjectForIdException {
        return seminarService.updateSeminar(id, seminar);
    }

    /**
     * Delete a single seminar
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public ResponseEntity<?> deleteSeminarById(@PathVariable long id) throws NoObjectForIdException {
        seminarService.deleteSeminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Custom response if no Seminar for the given ID exists
     */
    @ExceptionHandler(NoObjectForIdException.class)
    public ResponseEntity<ErrorResponse> noObjectException(NoObjectForIdException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("No seminar with id=" + ex.getId()),
                HttpStatus.NOT_FOUND);
    }
}
