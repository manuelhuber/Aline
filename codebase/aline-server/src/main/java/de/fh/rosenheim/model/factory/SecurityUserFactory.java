package de.fh.rosenheim.model.factory;

import de.fh.rosenheim.domain.entity.User;
import de.fh.rosenheim.model.security.SecurityUser;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUserFactory {

    public static SecurityUser create(User user) {
        return new SecurityUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getLastPasswordReset(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
        );
    }
}
