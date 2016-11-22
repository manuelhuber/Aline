package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.security.SecurityUser;

public interface SecurityService {

    boolean isDivisionHead();
    boolean canAccessUserData(SecurityUser user, User name);
}
