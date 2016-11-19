package de.fh.rosenheim.security.interfaces;

import de.fh.rosenheim.model.security.SecurityUser;

public interface SecurityService {

    Boolean hasProtectedAccess();
    boolean divisionHeadOrSelf(SecurityUser user, Long id);
}
