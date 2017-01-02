package de.fh.rosenheim.aline.model.dtos.factory;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.response.BookingSummaryDto;
import de.fh.rosenheim.aline.model.dtos.response.UserBookingDTO;
import de.fh.rosenheim.aline.model.dtos.response.UserDTO;
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

        List<BookingSummaryDto> bookings = new ArrayList<>();

        sortedBookings.forEach((year, userBookingDTOS) -> {
            BookingSummaryDto summary = new BookingSummaryDto();
            summary.setYear(year);
            summary.setBookings(userBookingDTOS);
            summary.setTotalSpending(userBookingDTOS.stream().mapToInt(UserBookingDTO::getSeminarCost).sum());
            bookings.add(summary);
        });

        dto.setBookings(bookings);

        return dto;
    }
}
