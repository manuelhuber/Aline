package de.fh.rosenheim.service;

import de.fh.rosenheim.domain.entity.Seminar;
import de.fh.rosenheim.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.repository.SeminarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SeminarService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeminarRepository seminarRepository;

    public Seminar getSeminar(long id) throws NoObjectForIdException {
        Seminar seminar = seminarRepository.findOne(id);
        if (seminar == null) {
            throw new NoObjectForIdException(id);
        }
        return seminar;
    }

    public Iterable<Seminar> getAllSeminars() {
        return seminarRepository.findAll();
    }

    public boolean deleteSeminar(long id) throws NoObjectForIdException {
        try {
            seminarRepository.delete(id);
            log.info(getUserName() + " deleted seminar with id " + id + "successfully");
            return true;
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(getUserName() + " tried to deleted non existing seminar with id " + id);
            throw new NoObjectForIdException(id);
        } catch (Exception e) {
            log.error(getUserName() + " tried to deleted seminar with id " + id + " but it failed.", e);
            return false;
        }
    }

    public Seminar createNewSeminar(Seminar seminar) {
        // Even if the id is set in the JSON body, we ignore it and create a new seminar
        seminar.setId(null);
        Seminar newSeminar = seminarRepository.save(seminar);
        log.info(getUserName() + " created a new seminar with id " + newSeminar.getId());
        return newSeminar;
    }

    private String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
