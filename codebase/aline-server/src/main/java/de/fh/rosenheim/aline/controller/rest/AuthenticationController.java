package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.json.request.AuthenticationRequest;
import de.fh.rosenheim.aline.model.json.response.AuthenticationResponse;
import de.fh.rosenheim.aline.security.service.AuthenticationService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${route.authentication.base}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ControllerUtil controllerUtil;

    public AuthenticationController(AuthenticationService authenticationService, ControllerUtil controllerUtil) {
        this.authenticationService = authenticationService;
        this.controllerUtil = controllerUtil;
    }

    /**
     * Validates the username & password and returns a token & authorities for the user
     */
    @RequestMapping(value = "${route.authentication.login}", method = RequestMethod.POST)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.loginUser(authenticationRequest);
    }

    /**
     * Sends a new token, if the old one is valid without the need for username & password
     */
    @RequestMapping(value = "${route.authentication.refresh}", method = RequestMethod.GET)
    public AuthenticationResponse authenticationRequest(HttpServletRequest request) {
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
