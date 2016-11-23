package de.fh.rosenheim.aline.util;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Manuel on 23.11.2016.
 */
public class LoggingUtil {

    public static String currentUser() {
        return "User with name '" + SecurityContextHolder.getContext().getAuthentication().getName() + "' ";
    }
}
