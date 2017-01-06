package de.fh.rosenheim.aline.util;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.dtos.booking.BookingDTO;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Manuel on 06.01.2017.
 */
@Component
public class BookingUtil {

    private final BookingFactory bookingFactory;
    private final UserFactory userFactory;

    public BookingUtil(BookingFactory bookingFactory, UserFactory userFactory) {
        this.bookingFactory = bookingFactory;
        this.userFactory = userFactory;
    }

    public BookingDTO generateBookingDTO(Booking booking) {
        UserDTO userDTO = userFactory.toUserDTO(booking.getUser());
        return bookingFactory.toBookingDTO(booking, userDTO);
    }
}
