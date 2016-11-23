package de.fh.rosenheim.aline.service;

import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.SeminarBasics;
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

    /**
     * Returns the seminar with the given ID
     */
    public Seminar getSeminar(long id) throws NoObjectForIdException {
        Seminar seminar = seminarRepository.findOne(id);
        if (seminar == null) {
            throw new NoObjectForIdException(id);
        }
        return seminar;
    }

    /**
     * Returns all available seminars
     */
    public Iterable<Seminar> getAllSeminars() {
        return seminarRepository.findAll();
    }

    /**
     * Deletes seminar with the given ID
     */
    public void deleteSeminar(long id) throws NoObjectForIdException {
        try {
            seminarRepository.delete(id);
            log.info(currentUser() + "deleted seminar with id=" + id + " successfully");
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(currentUser() + "tried to deleted non existing seminar with id=" + id);
            throw new NoObjectForIdException(id);
        } catch (Exception e) {
            log.error(currentUser() + "tried to deleted seminar with id=" + id + " but it failed.", e);
            throw e;
        }
    }

    /**
     * Creates a new seminar
     * Will always create a new seminar, even if the given seminar already has an ID
     */
    public Seminar createNewSeminar(SeminarBasics basics) {
        Seminar wrappedBasics = new Seminar();
        wrappedBasics.copyBasics(basics);
        Seminar newSeminar = seminarRepository.save(wrappedBasics);
        log.info(currentUser() + "created a new seminar with id " + newSeminar.getId());
        return newSeminar;
    }

    /**
     * Updates the seminar with the given ID with the given data
     * All properties of the existing seminar will be overwritten with the new data (even if it's null)
     */
    public Seminar updateSeminar(long id, SeminarBasics newSeminar) throws NoObjectForIdException {
        Seminar oldSeminar = getSeminar(id);
        oldSeminar.copyBasics(newSeminar);
        Seminar savedSeminar = seminarRepository.save(oldSeminar);
        log.info(currentUser() + "updated seminar with id " + savedSeminar.getId());
        return savedSeminar;
    }

    private String currentUser() {
        return "User with name '" + SecurityContextHolder.getContext().getAuthentication().getName() + "' ";
    }
}
