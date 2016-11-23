package de.fh.rosenheim.aline.util;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {

    public static String[] getAuthoritiesAsStringArray(UserDetails userDetails) {
        List<String> authoritiesList = new ArrayList<>();
        userDetails.getAuthorities().forEach(o -> authoritiesList.add(o.getAuthority()));
        String[] authoritiesArray = new String[authoritiesList.size()];
        return authoritiesList.toArray(authoritiesArray);
    }
}
