package de.fh.rosenheim.aline.model.factory;

import de.fh.rosenheim.aline.domain.entity.User;
import de.fh.rosenheim.aline.model.security.SecurityUser;
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
