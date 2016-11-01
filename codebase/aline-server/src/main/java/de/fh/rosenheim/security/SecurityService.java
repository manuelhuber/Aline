package de.fh.rosenheim.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * This service offers different kinds of authentication.
 * Useful when more complex checks have to be done.
 */
@Service
public class SecurityService {

    /**
     * This is a rather trivial example that could easily be done with annotations on the request.
     */
    public Boolean hasProtectedAccess() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(Roles.DIVISION_HEAD));
    }
}
