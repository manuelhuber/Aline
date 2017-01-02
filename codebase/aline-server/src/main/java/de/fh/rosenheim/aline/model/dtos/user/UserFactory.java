package de.fh.rosenheim.aline.model.dtos.user;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.booking.BookingSummaryDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserFactory {

    static public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
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

        List<BookingSummaryDTO> bookings = BookingFactory.toBookingSummaryDTOs(user.getBookings());
        dto.setBookings(bookings);

        return dto;
    }
}
