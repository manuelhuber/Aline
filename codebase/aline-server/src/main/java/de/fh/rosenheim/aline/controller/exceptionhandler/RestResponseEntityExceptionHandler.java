package de.fh.rosenheim.aline.controller.exceptionhandler;

import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handling
 * These methods are called when there is in uncaught exception and create a sensible REST conform HTTP Response
 * More specific exception handling can be defined in the controllers.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * The highest level handler that will only be called if no other handler fits
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> genericError(Exception exception) {
        log.error("Unhandled exception: ", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Something very unexpected happened"));
    }

    /**
     * Generic Autentication / Authorization response.
     * Note: SpringSecurity will in some cases (like @PreAuthorization) send it's own response
     */
    @ExceptionHandler(value = {AuthenticationException.class, AccessDeniedException.class})
    protected ResponseEntity<?> authorizationError(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(exception.getMessage()));
    }
}
