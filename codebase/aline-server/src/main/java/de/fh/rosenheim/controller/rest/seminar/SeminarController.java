package de.fh.rosenheim.controller.rest.seminar;

import com.google.common.collect.Iterables;
import de.fh.rosenheim.domain.entity.Seminar;
import de.fh.rosenheim.repository.SeminarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${route.seminar.base}")
public class SeminarController {

    @Autowired
    SeminarRepository seminarRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Seminar[]> getAllSeminars() {
        return ResponseEntity.ok(Iterables.toArray(seminarRepository.findAll(), Seminar.class));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Seminar> getSeminarById(@PathVariable long id) {
        return ResponseEntity.ok(seminarRepository.findOne(id));
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteSeminar(@PathVariable long id) {
        try {
            seminarRepository.delete(id);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Seminar seminar) {
        try {
            // Even if the id is set in the JSON body, we ignore it and create a new seminar
            seminar.setId(null);
            return new ResponseEntity<>(seminarRepository.save(seminar), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
