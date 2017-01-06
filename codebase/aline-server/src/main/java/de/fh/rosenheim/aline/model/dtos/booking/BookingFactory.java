package de.fh.rosenheim.aline.model.dtos.booking;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarFactory;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.util.DateUtil;
import de.fh.rosenheim.aline.util.SeminarUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Generates and transforms all Booking models & DTOs
 */
@Component
public class BookingFactory {

    private final DateUtil dateUtil;

    public BookingFactory(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    /**
     * Generates a BookingDTO for the given booking
     *
     * @return BookingDTO
     */
    public BookingDTO toBookingDTO(Booking booking, UserDTO userDTO) {
        return booking == null ?
                BookingDTO.builder().build() :
                BookingDTO.builder()
                        .id(booking.getId())
                        .created(booking.getCreated())
                        .updated(booking.getUpdated())
                        .status(booking.getStatus())
                        .seminar(SeminarFactory.toSeminarDTO(booking.getSeminar()))
                        .user(userDTO)
                        .build();
    }

    /**
     * Generates a UserBookingDTO for the given booking
     *
     * @return UserBookingDTO
     */
    public UserBookingDTO toUserBookingDTO(Booking booking) {
        Seminar seminar = booking.getSeminar();
        return UserBookingDTO.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .created(booking.getCreated())
                .updated(booking.getUpdated())
                .seminarId(seminar.getId())
                .seminarName(seminar.getName())
                .seminarCost(seminar.getCostsPerParticipant())
                .seminarYear(SeminarUtil.getYear(seminar))
                .seminarOver(SeminarUtil.getLastDate(seminar).before(dateUtil.getCurrentDate()))
                .build();
    }

    /**
     * Generates BookingSummaryDTOs for the given bookings
     *
     * @param bookings a group of bookings that should be grouped by year
     * @return List of BookingSummaryDTO, sorted by year (current year first)
     */
    public List<BookingSummaryDTO> toBookingSummaryDTOs(Collection<Booking> bookings) {
        List<BookingSummaryDTO> bookingSummaries = new ArrayList<>();

        if (bookings == null || bookings.size() == 0) {
            return bookingSummaries;
        }

        // UserBookings grouped by year
        Map<Integer, List<UserBookingDTO>> sortedBookings = bookings.stream()
                .map(this::toUserBookingDTO)
                .collect(Collectors.groupingBy(UserBookingDTO::getSeminarYear));

        sortedBookings.forEach((year, userBookingDTOS) -> bookingSummaries.add(BookingSummaryDTO.builder()
                .year(year)
                .bookings(userBookingDTOS)
                .plannedTotalSpending(userBookingDTOS.stream()
                        // Remove all denied seminars
                        .filter(booking -> !booking.getStatus().equals(BookingStatus.DENIED))
                        // Only allow future or past and granted seminars
                        .filter(booking -> !booking.isSeminarOver() || booking.getStatus().equals(BookingStatus.GRANTED))
                        .mapToLong(UserBookingDTO::getSeminarCost).sum())
                .plannedAdditionalSpending(userBookingDTOS.stream()
                        .filter(booking -> !booking.getStatus().equals(BookingStatus.DENIED) && !booking.isSeminarOver())
                        .mapToLong(UserBookingDTO::getSeminarCost).sum())
                .grantedSpending(userBookingDTOS.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.GRANTED))
                        .mapToLong(UserBookingDTO::getSeminarCost).sum())
                .issuedSpending(userBookingDTOS.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.GRANTED) && booking.isSeminarOver())
                        .mapToLong(UserBookingDTO::getSeminarCost).sum())
                .build()
        ));

        bookingSummaries.sort(Comparator.comparingInt(BookingSummaryDTO::getYear).reversed());

        return bookingSummaries;
    }
}
