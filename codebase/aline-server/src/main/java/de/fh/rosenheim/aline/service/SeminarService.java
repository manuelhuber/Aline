package de.fh.rosenheim.aline.service;

import de.fh.rosenheim.aline.domain.entity.Seminar;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.SeminarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SeminarService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SeminarRepository seminarRepository;

    public SeminarService(SeminarRepository seminarRepository) {
        this.seminarRepository = seminarRepository;
    }

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

    public void deleteSeminar(long id) throws NoObjectForIdException {
        try {
            seminarRepository.delete(id);
            log.info(getUserName() + " deleted seminar with id " + id + "successfully");
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(getUserName() + " tried to deleted non existing seminar with id " + id);
            throw new NoObjectForIdException(id);
        } catch (Exception e) {
            log.error(getUserName() + " tried to deleted seminar with id " + id + " but it failed.", e);
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
