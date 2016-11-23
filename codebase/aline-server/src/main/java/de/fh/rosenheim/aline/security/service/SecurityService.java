package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.security.utils.Authorities;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * This service offers different kinds of authentication.
 * Useful when more complex checks have to be done.
 */
@Service
public class SecurityService {

    private final UserDetailsService userDetailsService;

    public SecurityService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Users only get access to own data
     * Division Heads can access data of everybody in their division
     * Front Office can access everything
     */
    public boolean canAccessUserData(SecurityUser principal, User data) {
        return principal != null && data != null &&
                (isDivisionHeadForUser(principal, data) || isSelf(principal, data) || isFrontOffice(principal));
    }

    public boolean canCurrentUserChangeBookingStatus(Booking data) {
        SecurityUser principal = getCurrentUser();
        return principal != null && data != null &&
                (isDivisionHeadForUser(principal, data.getUser()) || isFrontOffice(principal));
    }

    public boolean isDivisionHeadForUser(SecurityUser principal, User data) {
        return principal.getAuthorities().contains(new SimpleGrantedAuthority(Authorities.DIVISION_HEAD)) &&
                principal.getDivision().equals(data.getDivision());
    }

    public boolean isSelf(SecurityUser principal, User data) {
        return principal.getUsername().equals(data.getUsername());
    }

    public boolean isFrontOffice(SecurityUser principal) {
        return principal.getAuthorities().contains(new SimpleGrantedAuthority(Authorities.FRONT_OFFICE));
    }

    private SecurityUser getCurrentUser() {
        return (SecurityUser) this.userDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }
}
