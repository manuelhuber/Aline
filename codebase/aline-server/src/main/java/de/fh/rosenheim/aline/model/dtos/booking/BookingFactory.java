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

public class BookingFactory {

    public static BookingDTO toBookingDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .created(booking.getCreated())
                .updated(booking.getUpdated())
                .status(booking.getStatus())
                .seminar(SeminarFactory.toSeminarDTO(booking.getSeminar()))
                .user(UserFactory.toUserDTO(booking.getUser()))
                .build();
    }

    public static UserBookingDTO toUserBookingDTO(Booking booking) {
        return UserBookingDTO.builder()
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

    public static List<BookingSummaryDTO> toBookingSummaryDTOs(Collection<Booking> bookings) {

        Map<Integer, List<UserBookingDTO>> sortedBookings = bookings.stream()
                .map(BookingFactory::toUserBookingDTO)
                .collect(Collectors.groupingBy(UserBookingDTO::getSeminarYear));

        List<BookingSummaryDTO> bookingSummaries = new ArrayList<>();

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
