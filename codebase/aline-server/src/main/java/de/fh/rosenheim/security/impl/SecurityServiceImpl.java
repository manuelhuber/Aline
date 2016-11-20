package de.fh.rosenheim.security.impl;

import de.fh.rosenheim.model.security.SecurityUser;
import de.fh.rosenheim.security.interfaces.SecurityService;
import de.fh.rosenheim.security.utils.Authorities;
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
     * Division Heads get access in generall
     */
    public boolean divisionHeadOrSelf(SecurityUser currentUser, Long userId) {
        return currentUser != null
                && (currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Authorities.DIVISION_HEAD))
                || currentUser.getId().equals(userId));
    }
}
