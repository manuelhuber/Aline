package de.fh.rosenheim.aline.model.json.factory;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.json.response.UserBookingDTO;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Manuel on 02.01.2017.
 */
public class BookingFactory {

    public static UserBookingDTO toUserBookingDTO(Booking booking) {
        UserBookingDTO userBookingDTO = new UserBookingDTO();

        userBookingDTO.setId(booking.getId());
        userBookingDTO.setStatus(booking.getStatus());
        userBookingDTO.setCreated(booking.getCreated());
        userBookingDTO.setUpdated(booking.getUpdated());
        userBookingDTO.setSeminarId(booking.getSeminar().getId());
        userBookingDTO.setSeminarName(booking.getSeminar().getName());
        userBookingDTO.setSeminarCost(booking.getSeminar().getCostsPerParticipant());

        List<Date> dates = Arrays.asList(booking.getSeminar().getDates());
        dates.sort(Date::compareTo);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dates.get(0));
        userBookingDTO.setSeminarYear(cal.get(Calendar.YEAR));

        return userBookingDTO;
    }
}
