package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.security.utils.Authorities;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * This service offers different kinds of authentication.
 * Useful when more complex checks have to be done.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    /**
     * This is a rather trivial example that could easily be done with annotations on the request.
     */
    public boolean isDivisionHead() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(Authorities.DIVISION_HEAD));
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
}
