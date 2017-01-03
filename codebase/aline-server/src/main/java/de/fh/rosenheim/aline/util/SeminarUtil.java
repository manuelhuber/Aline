package de.fh.rosenheim.aline.util;

import de.fh.rosenheim.aline.model.domain.Seminar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Manuel on 03.01.2017.
 */
public class SeminarUtil {

    /**
     * Returns the last date
     *
     * @param seminar
     * @return
     */
    public static Date getLastDate(Seminar seminar) {
        if (seminar.getDates() == null || seminar.getDates().length == 0) {
            return null;
        }
        List<Date> dates = Arrays.asList(seminar.getDates());
        dates.sort(Date::compareTo);
        return dates.get(dates.size() - 1);
    }

    /**
     * Returns the year of the seminar (to group seminars)
     * The last date
     *
     * @param seminar
     * @return
     */
    public static int getYear(Seminar seminar) {
        if (seminar.getDates() == null || seminar.getDates().length == 0) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(getLastDate(seminar));
        return cal.get(Calendar.YEAR);
    }
}
