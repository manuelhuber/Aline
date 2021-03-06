package de.fh.rosenheim.aline.util;

import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Simple utils for everything seminar related
 */
public class SeminarUtil {

    /**
     * Returns the last of the seminar
     *
     * @return the latest date or null
     */
    public static Date getLastDate(Seminar seminar) {
        return seminar.getDates() == null || seminar.getDates().length < 1
                ? null
                : Collections.max(Arrays.asList(seminar.getDates()));
    }

    /**
     * Returns the year of the seminar (to group seminars)
     * If the seminar has no dates (yet), the current year is returned
     *
     * @return the year of the last date or if no dates are set the current year
     */
    public static int getYear(Seminar seminar) {
        Date latestDate = getLastDate(seminar);

        Calendar cal = Calendar.getInstance();
        if (latestDate != null) {
            cal.setTime(latestDate);
        }

        return cal.get(Calendar.YEAR);
    }

    /**
     * Returns the number of active bookings. These are the bookings that are taken into account when checking if the
     * seminar is already fully booked
     *
     * @return The number of active bookings
     */
    public static int getActiveBookingCount(Seminar seminar) {
        return seminar.getBookings()
                .stream()
                .filter(booking -> !booking.getStatus().equals(BookingStatus.DENIED))
                .collect(Collectors.toList()).size();
    }
}
