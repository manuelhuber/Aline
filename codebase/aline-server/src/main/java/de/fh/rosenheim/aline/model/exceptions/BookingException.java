package de.fh.rosenheim.aline.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * A exception caused in some way by user input (like trying to book a unbookable seminar)
 */
public class BookingException extends Exception {

    String message;
}
