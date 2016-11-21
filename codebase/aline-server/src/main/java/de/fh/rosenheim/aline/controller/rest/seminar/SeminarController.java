package de.fh.rosenheim.aline.controller.rest.seminar;

import de.fh.rosenheim.aline.domain.entity.Seminar;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.service.SeminarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${route.seminar.base}")
public class SeminarController {

    private final SeminarService seminarService;

    public SeminarController(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    /**
     * @return All seminars
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Seminar> getAllSeminars() {
        return seminarService.getAllSeminars();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Seminar addSeminar(@RequestBody Seminar seminar) {
        return seminarService.createNewSeminar(seminar);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Seminar getSeminarById(@PathVariable long id) throws NoObjectForIdException {
        return seminarService.getSeminar(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteSeminarById(@PathVariable long id) throws NoObjectForIdException {
        seminarService.deleteSeminar(id);
        return ResponseEntity.ok(null);
    }

    @ExceptionHandler(NoObjectForIdException.class)
    public ResponseEntity<ErrorResponse> noObjectException(NoObjectForIdException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("No seminar with id=" + ex.getId()),
                HttpStatus.NOT_FOUND);
    }
}
