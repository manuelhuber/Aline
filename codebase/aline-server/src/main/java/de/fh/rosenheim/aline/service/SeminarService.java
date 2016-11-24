package de.fh.rosenheim.aline.service;

import com.google.common.collect.Lists;
import de.fh.rosenheim.aline.model.domain.Category;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.SeminarBasics;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.exceptions.UnkownCategoryException;
import de.fh.rosenheim.aline.repository.CategoryRepository;
import de.fh.rosenheim.aline.repository.SeminarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.fh.rosenheim.aline.util.LoggingUtil.currentUser;

@Service
public class SeminarService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SeminarRepository seminarRepository;
    private final CategoryRepository categoryRepository;

    public SeminarService(SeminarRepository seminarRepository, CategoryRepository categoryRepository) {
        this.seminarRepository = seminarRepository;
        this.categoryRepository = categoryRepository;
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
    public Seminar createNewSeminar(SeminarBasics basics) throws UnkownCategoryException {
        Seminar seminar = new Seminar(basics);
        checkCategory(seminar.getCategory());
        seminarRepository.save(seminar);
        log.info(currentUser() + "created a new seminar with id " + seminar.getId());
        return seminar;
    }

    /**
     * Updates the seminar with the given ID with the given data
     * All properties of the existing seminar will be overwritten with the new data (even if it's null)
     */
    public Seminar updateSeminar(long id, SeminarBasics newSeminar) throws NoObjectForIdException, UnkownCategoryException {
        Seminar seminar = getSeminar(id);
        seminar.copyBasics(newSeminar);
        checkCategory(seminar.getCategory());
        seminarRepository.save(seminar);
        log.info(currentUser() + "updated seminar with id " + seminar.getId());
        return seminar;
    }

    /**
     * Returns all available Categories
     *
     * @return A list of categories
     */
    public List<String> getAllCategories() {
        return Lists.newArrayList(this.categoryRepository.findAll().iterator())
                .stream().map(Category::getName).collect(Collectors.toList());
    }

    /**
     * Checks the given category against the data from the database
     *
     * @param category The name of the category
     * @throws UnkownCategoryException if the given category is unknown
     */
    private void checkCategory(String category) throws UnkownCategoryException {
        List<String> categories = getAllCategories();
        if (categories.contains(category)) {
            throw new UnkownCategoryException(categories);

        }
    }
}
