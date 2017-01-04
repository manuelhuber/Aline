package de.fh.rosenheim.aline.model.dtos.user;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.booking.BookingSummaryDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates and transforms all User models & DTOs
 */
public class UserFactory {

    /**
     * Generate a UserDTO from the given user
     *
     * @return UserDTO
     */
    static public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setUserName(user.getUsername());
        dto.setAuthorities(
                user.getAuthorities() == null ? new ArrayList<>() :
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
