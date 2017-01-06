package de.fh.rosenheim.aline.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateUtil {

    public Date getCurrentDate() {
        return new Date();
    }
}
