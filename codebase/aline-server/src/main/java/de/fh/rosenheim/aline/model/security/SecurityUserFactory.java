package de.fh.rosenheim.aline.model.security;

import de.fh.rosenheim.aline.model.domain.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUserFactory {

    public static SecurityUser create(User user) {
        return new SecurityUser(
                user.getUsername(),
                user.getPassword(),
                user.getDivision(),
                user.getLastPasswordReset(),
                user.getLastLogout(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
        );
    }
}
