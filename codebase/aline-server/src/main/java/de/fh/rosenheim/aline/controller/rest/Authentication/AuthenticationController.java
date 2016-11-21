package de.fh.rosenheim.aline.controller.rest.Authentication;

import de.fh.rosenheim.aline.model.json.request.AuthenticationRequest;
import de.fh.rosenheim.aline.model.json.response.AuthenticationResponse;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${route.authentication.base}")
public class AuthenticationController {

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "${route.authentication.login}", method = RequestMethod.POST)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.loginUser(authenticationRequest);
    }

    /**
     * Sends a new token, if the old one is valid without the need for username & password
     */
    @RequestMapping(value = "${route.authentication.refresh}", method = RequestMethod.GET)
    public AuthenticationResponse authenticationRequest(HttpServletRequest request) {
        return authenticationService.refreshToken(request.getHeader(this.tokenHeader));
    }

    /**
     * Logs the user out, making all tokens granted before the logout invalid
     */
    @RequestMapping(value = "${route.authentication.logout}", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authenticationService.logoutUser(request.getHeader(this.tokenHeader));
        return ResponseEntity.ok(null);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> invalidLogin(AuthenticationException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }
}
