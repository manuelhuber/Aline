package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.domain.entity.User;
import de.fh.rosenheim.aline.model.security.SecurityUser;

public interface SecurityService {

    boolean isDivisionHead();
    boolean divisionHeadOrSelf(SecurityUser user, User name);
}
