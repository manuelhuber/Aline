package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.dtos.authentication.AuthenticationRequestDTO;
import de.fh.rosenheim.aline.model.dtos.authentication.AuthenticationDTO;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.security.service.AuthenticationService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * All HTTP endpoints related to authentication
 */
@RestController
@RequestMapping("${route.authentication.base}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ControllerUtil controllerUtil;

    public AuthenticationController(AuthenticationService authenticationService, ControllerUtil controllerUtil) {
        this.authenticationService = authenticationService;
        this.controllerUtil = controllerUtil;
    }

    // ------------------------------------------------------------------------------------------ Authentication Handler

    /**
     * Validates the username & password and returns a token & authorities for the user
     *
     * @param authenticationRequest username & password
     * @return A token & all authorities of the user
     */
    @RequestMapping(value = "${route.authentication.login}", method = RequestMethod.POST)
    public AuthenticationDTO login(@RequestBody AuthenticationRequestDTO authenticationRequest) throws NoObjectForIdException {
        return authenticationService.loginUser(authenticationRequest);
    }

    /**
     * Sends a new token, if the old one is valid without the need for username & password
     *
     * @return A token & all authorities of the user
     */
    @RequestMapping(value = "${route.authentication.refresh}", method = RequestMethod.GET)
    public AuthenticationDTO authenticationRequest(HttpServletRequest request) throws NoObjectForIdException {
        return authenticationService.refreshToken(controllerUtil.getToken(request));
    }

    /**
     * Logs the user out, making all tokens granted before the logout invalid
     */
    @RequestMapping(value = "${route.authentication.logout}", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authenticationService.logoutUser(controllerUtil.getToken(request));
        return ResponseEntity.ok(null);
    }
}
