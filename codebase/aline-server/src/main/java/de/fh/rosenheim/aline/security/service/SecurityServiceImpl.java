package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.domain.entity.User;
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
     * Division Heads get access in generall
     */
    public boolean divisionHeadOrSelf(SecurityUser securityUser, User user) {
        return securityUser != null && user != null
                && ((securityUser.getAuthorities().contains(new SimpleGrantedAuthority(Authorities.DIVISION_HEAD)) &&
                securityUser.getDivision().equals(user.getDivision()))
                || securityUser.getUsername().equals(user.getUsername()));
    }
}
