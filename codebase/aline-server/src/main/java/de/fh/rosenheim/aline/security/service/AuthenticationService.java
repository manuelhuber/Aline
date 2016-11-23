package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.model.exceptions.InvalidTokenException;
import de.fh.rosenheim.aline.model.json.request.AuthenticationRequest;
import de.fh.rosenheim.aline.model.json.response.AuthenticationResponse;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.security.utils.TokenUtils;
import de.fh.rosenheim.aline.service.UserService;
import de.fh.rosenheim.aline.util.UserUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenUtils tokenUtils;

    public AuthenticationService(UserService u, TokenUtils t, AuthenticationManager a, UserDetailsService ud) {
        this.userService = u;
        this.tokenUtils = t;
        this.authenticationManager = a;
        this.userDetailsService = ud;
    }

    /**
     * Authenticates the user in the SecurityContext
     *
     * @param request Username & Password
     * @return AuthenticationResponse Token & Authorities
     * @throws AuthenticationException if login data is invalid
     */
    public AuthenticationResponse loginUser(AuthenticationRequest request) throws AuthenticationException {
        // Perform authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.tokenUtils.generateToken(userDetails);
        return new AuthenticationResponse(token, UserUtil.getAuthoritiesAsStringArray(userDetails));
    }

    /**
     * Returns a fresh token if the given one is valid
     *
     * @param token
     * @return AuthenticationResponse Token & Authorities
     * @throws AuthenticationException If the token is no longer valid
     */
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
            return new AuthenticationResponse(refreshedToken, UserUtil.getAuthoritiesAsStringArray(user));
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
