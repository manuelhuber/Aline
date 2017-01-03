package de.fh.rosenheim.aline.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.domain.Category;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.dtos.bill.BillDTO;
import de.fh.rosenheim.aline.model.dtos.generic.ErrorResponse;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarBasicsDTO;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarDTO;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarFactory;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.exceptions.UnknownCategoryException;
import de.fh.rosenheim.aline.service.SeminarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * All HTTP endpoints related to seminars
 */
@RestController
@RequestMapping("${route.seminar.base}")
public class SeminarsController {

    private final SeminarService seminarService;

    public SeminarsController(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    // ------------------------------------------------------------------------------------------------- Seminar Handler

    /**
     * Get all seminars
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<SeminarDTO> getAllSeminars() {
        return toDto(seminarService.getAllSeminars());
    }

    /**
     * Get all seminars
     *
     * @return a Iterable over all Seminars (which will be serialized as array in JSON)
     */
    @RequestMapping(value = "${route.seminar.past}", method = RequestMethod.GET)
    public List<SeminarDTO> getPastSeminars() {
        return toDto(seminarService.getPastSeminars());
    }

    /**
     * Get all seminars
     *
     * @return a Iterable over all Seminars (which will be serialized as array in JSON)
     */
    @RequestMapping(value = "${route.seminar.current}", method = RequestMethod.GET)
    public List<SeminarDTO> getCurrentSeminars() {
        return toDto(seminarService.getCurrentSeminars());
    }

    /**
     * Add a new seminar
     *
     * @param seminar The basic info about the seminar
     * @return The complete newly created seminar
     * @throws UnknownCategoryException if the category is not a known category (all categories will be listed in the
     *                                 response)
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public ResponseEntity<SeminarDTO> addSeminar(@RequestBody SeminarBasicsDTO seminar) throws UnknownCategoryException {
        return new ResponseEntity<>(
                SeminarFactory.toSeminarDTO(seminarService.createNewSeminar(seminar)),
                HttpStatus.CREATED
        );
    }

    /**
     * Get a single seminar
     *
     * @param id of the Seminar
     * @return the complete seminar
     * @throws NoObjectForIdException if there is no seminar for the given ID
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public SeminarDTO getSeminarById(@PathVariable long id) throws NoObjectForIdException {
        return SeminarFactory.toSeminarDTO(seminarService.getSeminar(id));
    }

    /**
     * Update basic info of a seminar (no ID, creation date, update date or bookings)
     * Properties that are not set in the seminar parameter will be set to null/0
     *
     * @param id      of the seminar that should be updated
     * @param seminar the basic data that will be set
     * @return The updated Seminar
     * @throws NoObjectForIdException  if there is no seminar for the given ID
     * @throws UnknownCategoryException if the category is not a known category (all categories will be listed in the
     *                                 response)
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public SeminarDTO updateSeminar(@PathVariable long id, @RequestBody SeminarBasicsDTO seminar)
            throws NoObjectForIdException, UnknownCategoryException {
        return SeminarFactory.toSeminarDTO(seminarService.updateSeminar(id, seminar));
    }

    /**
     * Delete a single seminar
     *
     * @param id of the seminar that should be deleted
     * @throws NoObjectForIdException if there is no seminar for the given ID
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public ResponseEntity<?> deleteSeminarById(@PathVariable long id) throws NoObjectForIdException {
        seminarService.deleteSeminar(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------------------------------------------------------------- Bill Handler

    /**
     * Generate the bill for the seminar
     *
     * @param id of the Seminar
     * @return the bill
     * @throws NoObjectForIdException if there is no seminar for the given ID
     */
    @RequestMapping(value = "{id}/${route.seminar.bill}", method = RequestMethod.GET)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    @JsonView(View.BillView.class)
    public BillDTO generateBillForSeminar(@PathVariable long id) throws NoObjectForIdException {
        return seminarService.getBill(id);
    }

    // ------------------------------------------------------------------------------------------------ Category Handler

    /**
     * Get all categories
     *
     * @return A list of all category names
     */
    @RequestMapping(value = "${route.seminar.category}", method = RequestMethod.GET)
    public List<String> getAllCategories() {
        return seminarService.getAllCategories();
    }

    /**
     * Add a category
     *
     * @return A updated list of all category names
     */
    @RequestMapping(value = "${route.seminar.category}", method = RequestMethod.POST)
    public List<String> addCategory(@RequestBody Category category) {
        seminarService.addCategory(category);
        return seminarService.getAllCategories();
    }

    /**
     * Delete a category
     *
     * @return A updated list of all category names
     */
    @RequestMapping(value = "${route.seminar.category}", method = RequestMethod.DELETE)
    public List<String> deleteCategory(@RequestParam String categoryName) {
        seminarService.deleteCategory(categoryName);
        return seminarService.getAllCategories();
    }

    // ----------------------------------------------------------------------------------------------- Exception Handler

    /**
     * Custom response if no Seminar / Category for the given ID exists
     */
    @ExceptionHandler(NoObjectForIdException.class)
    public ResponseEntity<ErrorResponse> noObjectException(NoObjectForIdException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("No " + ex.getObject().getName() + " with id='" + ex.getId() + "'"),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Custom response if no Seminar for the given ID exists
     */
    @ExceptionHandler(UnknownCategoryException.class)
    public ResponseEntity<ErrorResponse> categoryNotFoundException(UnknownCategoryException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "You entered an invalid category. Allowed values are: " + ex.getAllowedCategories().toString()),
                HttpStatus.NOT_FOUND);
    }

    private List<SeminarDTO> toDto(Iterable<Seminar> seminars) {
        return StreamSupport
                .stream(seminars.spliterator(), false)
                .map(SeminarFactory::toSeminarDTO)
                .collect(Collectors.toList());
    }
}
