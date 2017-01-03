package de.fh.rosenheim.aline.util;

/**
 * Define texts for swagger here to avoid duplicates and allow easy changes
 */
public class SwaggerTexts {

    public final static String GET_USER_DATA = "Returns the data for the given user or if no username is given from the current user (detected via token).";

    public final static String GET_DIVISION_USERS = "Returns the users for the given division or if no division is given, the division from the current user is used (detected via token). ";

    /**
     * Getting data about a user other than yourself
     */
    public final static String SENSITIVE_DATA = "Accessing other user's data requires specific authorities.";

    public final static String CURRENCY = "In euro cent. Example: 1234 = 12,34 Euro";

    public final static String ACTIVE_BOOKINGS = "All non-denied bookings (including requested but not yet granted)";

    public final static String PLANNED_SPENDING = "The total cost of all granted & requested seminars. " + CURRENCY;
    public final static String GRANTED_SPENDING = "The total cost of all granted seminars. " + CURRENCY;
}
