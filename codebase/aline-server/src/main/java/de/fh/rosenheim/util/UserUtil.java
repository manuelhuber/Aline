package de.fh.rosenheim.util;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel on 15.11.2016.
 */
public class UserUtil {

    public static String[] getAuthorityStringsAsArray(UserDetails userDetails) {
        List<String> authoritiesList = new ArrayList<>();
        userDetails.getAuthorities().forEach(o -> authoritiesList.add(o.getAuthority()));
        String[] authoritiesArray = new String[authoritiesList.size()];
        return authoritiesList.toArray(authoritiesArray);
    }
}
