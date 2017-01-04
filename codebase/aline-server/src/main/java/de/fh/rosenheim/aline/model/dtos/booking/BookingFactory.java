package de.fh.rosenheim.aline.model.dtos.booking;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarFactory;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import de.fh.rosenheim.aline.util.SeminarUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generates and transforms all Booking models & DTOs
 */
public class BookingFactory {

    /**
     * Generates a BookingDTO for the given booking
     *
     * @return BookingDTO
     */
    public static BookingDTO toBookingDTO(Booking booking) {
        return booking == null ?
                BookingDTO.builder().build() :
                BookingDTO.builder()
                        .id(booking.getId())
                        .created(booking.getCreated())
                        .updated(booking.getUpdated())
                        .status(booking.getStatus())
                        .seminar(SeminarFactory.toSeminarDTO(booking.getSeminar()))
                        .user(UserFactory.toUserDTO(booking.getUser()))
                        .build();
    }

    /**
     * Generates a UserBookingDTO for the given booking
     *
     * @return UserBookingDTO
     */
    public static UserBookingDTO toUserBookingDTO(Booking booking) {
        return booking == null ?
                UserBookingDTO.builder().build() :
                UserBookingDTO.builder()
                        .id(booking.getId())
                        .status(booking.getStatus())
                        .created(booking.getCreated())
                        .updated(booking.getUpdated())
                        .seminarId(booking.getSeminar().getId())
                        .seminarName(booking.getSeminar().getName())
                        .seminarCost(booking.getSeminar().getCostsPerParticipant())
                        .seminarYear(SeminarUtil.getYear(booking.getSeminar()))
                        .build();
    }

    /**
     * Generates BookingSummaryDTOs for the given bookings
     *
     * @param bookings a group of bookings that should be grouped by year
     * @return List of BookingSummaryDTO
     */
    public static List<BookingSummaryDTO> toBookingSummaryDTOs(Collection<Booking> bookings) {
        List<BookingSummaryDTO> bookingSummaries = new ArrayList<>();

        if (bookings == null || bookings.size() == 0) {
            return bookingSummaries;
        }

        // UserBookings grouped by year
        Map<Integer, List<UserBookingDTO>> sortedBookings = bookings.stream()
                .map(BookingFactory::toUserBookingDTO)
                .collect(Collectors.groupingBy(UserBookingDTO::getSeminarYear));

        sortedBookings.forEach((year, userBookingDTOS) -> bookingSummaries.add(BookingSummaryDTO.builder()
                .year(year)
                .bookings(userBookingDTOS)
                .plannedSpending(userBookingDTOS.stream()
                        .filter(booking -> !booking.getStatus().equals(BookingStatus.DENIED))
                        .mapToLong(UserBookingDTO::getSeminarCost).sum())
                .grantedSpending(userBookingDTOS.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.GRANTED))
                        .mapToLong(UserBookingDTO::getSeminarCost).sum())
                .build()
        ));
        return bookingSummaries;
    }
}
