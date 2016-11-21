package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.model.exceptions.InvalidTokenException;
import de.fh.rosenheim.aline.model.json.request.AuthenticationRequest;
import de.fh.rosenheim.aline.model.json.response.AuthenticationResponse;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.security.utils.TokenUtils;
import de.fh.rosenheim.aline.service.UserService;
import de.fh.rosenheim.aline.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by Manuel on 21.11.2016.
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Takes the login data and returns a
     *
     * @param request
     * @return AuthenticationResponse
     * @throws AuthenticationException if login data is invalid
     */
    public AuthenticationResponse loginUser(AuthenticationRequest request) throws AuthenticationException {
        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.tokenUtils.generateToken(userDetails);
        // Return the token
        return new AuthenticationResponse(token, UserUtil.getAuthorityStringsAsArray(userDetails));
    }

    public AuthenticationResponse refreshToken(String token) throws AuthenticationException {
        SecurityUser user;

        try {
            user = (SecurityUser) this.userDetailsService.loadUserByUsername(
                    this.tokenUtils.getUsernameFromToken(token));
        } catch (AuthenticationException e) {
            throw invalidToken();
        }

        if (this.tokenUtils.canTokenBeRefreshed(token, user)) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return new AuthenticationResponse(refreshedToken, UserUtil.getAuthorityStringsAsArray(user));
        } else throw invalidToken();
    }

    public void logoutUser(String token) throws AuthenticationException {
        String username = this.tokenUtils.getUsernameFromToken(token);
        SecurityUser user;

        try {
            user = (SecurityUser) this.userDetailsService.loadUserByUsername(username);
        } catch (AuthenticationException e) {
            throw invalidToken();
        }

        if (this.tokenUtils.validateToken(token, user)) {
            this.userService.logout(username);
        } else throw invalidToken();
    }

    private AuthenticationException invalidToken() {
        return new InvalidTokenException("The token you sent is not valid");
    }
}
