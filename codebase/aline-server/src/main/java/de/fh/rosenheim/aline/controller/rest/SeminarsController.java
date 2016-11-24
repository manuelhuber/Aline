package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.SeminarBasics;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.exceptions.UnkownCategoryException;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.service.SeminarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All HTTP enpoints related to seminars
 */
@RestController
@RequestMapping("${route.seminar.base}")
public class SeminarsController {

    private final SeminarService seminarService;

    public SeminarsController(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    /**
     * Get all seminars
     *
     * @return a Iterable over all Seminars (which will be serialized as array in JSON)
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Seminar> getAllSeminars() {
        return seminarService.getAllSeminars();
    }

    /**
     * Add a new seminar
     *
     * @param seminar The basic info about the seminar
     * @return The complete newly created seminar
     * @throws UnkownCategoryException If the
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public ResponseEntity<Seminar> addSeminar(@RequestBody SeminarBasics seminar) throws UnkownCategoryException {
        return new ResponseEntity<>(seminarService.createNewSeminar(seminar), HttpStatus.CREATED);
    }

    /**
     * Get all categories
     *
     * @return A list of all category names
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<String> getAllCategories() {
        return seminarService.getAllCategories();
    }

    /**
     * Get a single seminar
     *
     * @param id of the Seminar
     * @return the complete seminar
     * @throws NoObjectForIdException if there is no seminar for the given ID
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Seminar getSeminarById(@PathVariable long id) throws NoObjectForIdException {
        return seminarService.getSeminar(id);
    }

    /**
     * Update basic info of a seminar (no ID, creation date, update date or bookings)
     * Properties that are not set in the seminar parameter will be set to null/0
     *
     * @param id      of the seminar that should be updated
     * @param seminar the basic data that will be set
     * @return The updated Seminar
     * @throws NoObjectForIdException  if there is no seminar for the given ID
     * @throws UnkownCategoryException if the category is not a known category (all categories will be listed in the
     *                                 response
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public Seminar updateSeminar(@PathVariable long id, @RequestBody SeminarBasics seminar) throws NoObjectForIdException, UnkownCategoryException {
        return seminarService.updateSeminar(id, seminar);
    }

    /**
     * Delete a single seminar
     *
     * @param id of the seminar that should be deleted
     * @return
     * @throws NoObjectForIdException if there is no seminar for the given ID
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

    /**
     * Custom response if no Seminar for the given ID exists
     */
    @ExceptionHandler(UnkownCategoryException.class)
    public ResponseEntity<ErrorResponse> categoryNotFoundException(UnkownCategoryException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "You entered an invalid category. Allowed values are:" + ex.getAllowedCategories().toString()),
                HttpStatus.NOT_FOUND);
    }
}
