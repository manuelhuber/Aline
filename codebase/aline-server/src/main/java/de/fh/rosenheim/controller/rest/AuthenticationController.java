package de.fh.rosenheim.controller.rest;

import de.fh.rosenheim.model.json.request.AuthenticationRequest;
import de.fh.rosenheim.model.json.response.AuthenticationResponse;
import de.fh.rosenheim.model.security.SecurityUser;
import de.fh.rosenheim.security.utils.TokenUtils;
import de.fh.rosenheim.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${route.authentication}")
public class AuthenticationController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = this.tokenUtils.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new AuthenticationResponse(token, getAuthoritiesFromUser(userDetails)));
    }

    /**
     * Sends a new token, if the old one is valid without the need for username & password
     */
    @RequestMapping(value = "${route.refresh}", method = RequestMethod.GET)
    public ResponseEntity<AuthenticationResponse> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        SecurityUser user = (SecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken, getAuthoritiesFromUser(user)));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Logs the user out, making all tokens granted before the logout invalid
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        SecurityUser user = (SecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.validateToken(token, user)) {
            this.userService.logout(username);
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private String[] getAuthoritiesFromUser(UserDetails userDetails) {
        List<String> authoritiesList = new ArrayList<>();
        userDetails.getAuthorities().forEach(o -> authoritiesList.add(o.getAuthority()));
        String[] authoritiesArray = new String[authoritiesList.size()];
        return authoritiesList.toArray(authoritiesArray);
    }
}
