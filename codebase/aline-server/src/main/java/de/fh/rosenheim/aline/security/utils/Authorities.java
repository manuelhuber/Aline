package de.fh.rosenheim.aline.security.utils;

public class Authorities {

    /**
     * The manager of a whole division
     */
    public static final String DIVISION_HEAD = "DIVISION_HEAD";
    /**
     * Every user
     */
    public static final String EMPLOYEE = "EMPLOYEE";
    /**
     * Front Office who need the authority to change just about anyhting
     */
    public static final String FRONT_OFFICE = "FRONT_OFFICE";
    /**
     * Users whose booking requests are automatically granted
     */
    public static final String TOP_DOG = "TOP_DOG";
}
