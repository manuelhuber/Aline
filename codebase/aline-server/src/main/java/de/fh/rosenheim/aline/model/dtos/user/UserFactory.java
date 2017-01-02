package de.fh.rosenheim.aline.model.dtos.user;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.booking.BookingSummaryDTO;
import de.fh.rosenheim.aline.model.dtos.booking.UserBookingDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserFactory {

    static public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserName(user.getUsername());
        dto.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
        dto.setDivision(user.getDivision());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        List<UserBookingDTO> bookingDTOs =
                user.getBookings().stream().map(BookingFactory::toUserBookingDTO).collect(Collectors.toList());

        Map<Integer, List<UserBookingDTO>> sortedBookings =
                bookingDTOs.stream().collect(Collectors.groupingBy(UserBookingDTO::getSeminarYear));

        List<BookingSummaryDTO> bookings = new ArrayList<>();

        sortedBookings.forEach((year, userBookingDTOS) -> {
            BookingSummaryDTO summary = new BookingSummaryDTO();
            summary.setYear(year);
            summary.setBookings(userBookingDTOS);
            summary.setTotalSpending(userBookingDTOS.stream().mapToInt(UserBookingDTO::getSeminarCost).sum());
            bookings.add(summary);
        });

        dto.setBookings(bookings);

        return dto;
    }
}
