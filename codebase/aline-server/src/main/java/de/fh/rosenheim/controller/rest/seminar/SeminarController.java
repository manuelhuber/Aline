package de.fh.rosenheim.controller.rest.seminar;

import com.google.common.collect.Iterables;
import de.fh.rosenheim.domain.entity.Seminar;
import de.fh.rosenheim.repository.SeminarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${route.seminar.base}")
public class SeminarController {

    @Autowired
    SeminarRepository seminarRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Seminar[]> getAllSeminars() {
        return ResponseEntity.ok(Iterables.toArray(seminarRepository.findAll(), Seminar.class));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Seminar seminar) {
        try {

            // Even if the id is set in the JSON body, we ignore it and create a new seminar
            seminar.setId(null);
            return new ResponseEntity<Seminar>(seminarRepository.save(seminar), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<Seminar>(HttpStatus.BAD_REQUEST);
        }
    }
}
