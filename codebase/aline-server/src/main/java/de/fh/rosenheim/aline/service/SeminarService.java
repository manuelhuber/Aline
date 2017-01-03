package de.fh.rosenheim.aline.service;

import com.google.common.collect.Lists;
import de.fh.rosenheim.aline.model.domain.Category;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.dtos.bill.BillDTO;
import de.fh.rosenheim.aline.model.dtos.bill.BillFactory;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarBasicsDTO;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarFactory;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.exceptions.UnknownCategoryException;
import de.fh.rosenheim.aline.repository.CategoryRepository;
import de.fh.rosenheim.aline.repository.SeminarRepository;
import de.fh.rosenheim.aline.util.SeminarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new NoObjectForIdException(Seminar.class, id);
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
     * Returns all seminars that have no more dates in the future
     */
    public Iterable<Seminar> getPastSeminars() {
        Date today = new Date();
        return filterForSeminarsByDate(Lists.newArrayList(seminarRepository.findAll()), today, true);
    }

    /**
     * Returns all seminars that have at least 1 date in the future
     */
    public Iterable<Seminar> getCurrentSeminars() {
        Date today = new Date();
        return filterForSeminarsByDate(Lists.newArrayList(seminarRepository.findAll()), today, false);
    }

    /**
     * Deletes seminar with the given ID
     */
    public void deleteSeminar(long id) throws NoObjectForIdException {
        try {
            seminarRepository.delete(id);
            log.info(currentUser() + "deleted seminar with id=" + id + " successfully.");
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(currentUser() + "tried to deleted non existing seminar with id=" + id + " unsuccessfully.");
            throw new NoObjectForIdException(Seminar.class, id);
        } catch (Exception e) {
            log.error(currentUser() + "tried to deleted seminar with id=" + id + " unsuccessfully.", e);
            throw e;
        }
    }

    /**
     * Creates a new seminar
     * Will always create a new seminar, even if the given seminar already has an ID
     */
    public Seminar createNewSeminar(SeminarBasicsDTO basics) throws UnknownCategoryException {
        Seminar seminar = SeminarFactory.createSeminar(basics);
        checkCategory(seminar.getCategory());
        seminarRepository.save(seminar);
        log.info(currentUser() + "created a new seminar with id=" + seminar.getId() + " successfully.");
        return seminar;
    }

    /**
     * Updates the seminar with the given ID with the given data
     * All properties of the existing seminar will be overwritten with the new data (even if it's null)
     */
    public Seminar updateSeminar(long id, SeminarBasicsDTO newSeminarData) throws NoObjectForIdException, UnknownCategoryException {
        Seminar seminar = getSeminar(id);
        SeminarFactory.updateSeminar(seminar, newSeminarData);
        checkCategory(seminar.getCategory());
        seminarRepository.save(seminar);
        log.info(currentUser() + "updated seminar with id=" + seminar.getId() + " successfully.");
        return seminar;
    }

    /**
     * Generates a bill for the given seminar
     */
    public BillDTO getBill(long id) throws NoObjectForIdException {
        Seminar seminar = getSeminar(id);
        BillDTO bill = BillFactory.generateBill(seminar);
        seminar.setBillGenerated(true);
        seminarRepository.save(seminar);
        return bill;
    }

    /**
     * Returns all available Categories
     *
     * @return A list of categories
     */
    public List<String> getAllCategories() {
        return Lists.newArrayList(this.categoryRepository.findAll())
                .stream().map(Category::getName).collect(Collectors.toList());
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
        log.info(currentUser() + "created a new seminar with id=" + category.getName() + "successfully.");
    }

    public void deleteCategory(String categoryName) {
        categoryRepository.delete(categoryName);
    }

    /**
     * Checks the given category against the data from the database
     *
     * @param category The name of the category
     * @throws UnknownCategoryException if the given category is unknown
     */
    private void checkCategory(String category) throws UnknownCategoryException {
        List<String> categories = getAllCategories();
        if (!categories.contains(category)) {
            throw new UnknownCategoryException(categories);
        }
    }

    /**
     * Filters seminars in regards if they are "in the past" of a given date or not.
     * All dates of the seminar are before the given date = seminar is before the given date
     * At least 1 date of the seminar is after the given date = seminar after the given date
     *
     * @param seminars        all seminars
     * @param referenceDate   the date used for comparison
     * @param getPastSeminars true = return seminars that are before the date
     *                        false = return seminars that are after the given date
     * @return A list of seminars either in the past or future compared to the given date
     */
    private List<Seminar> filterForSeminarsByDate(List<Seminar> seminars, Date referenceDate, boolean getPastSeminars) {
        return seminars.stream().filter(seminar -> {
            Date latestDate = SeminarUtil.getLastDate(seminar);
            // If there are no dates, return it anyways to ensure no seminar is lost because it doesn't have dates
            return latestDate == null || latestDate.after(referenceDate) != getPastSeminars;
        }).collect(Collectors.toList());
    }
}
