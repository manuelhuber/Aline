package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.authentication.AuthenticationDTO;
import de.fh.rosenheim.aline.model.dtos.authentication.AuthenticationRequestDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import de.fh.rosenheim.aline.model.exceptions.InvalidTokenException;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.repository.UserRepository;
import de.fh.rosenheim.aline.security.utils.TokenUtils;
import de.fh.rosenheim.aline.service.UserService;
import de.fh.rosenheim.aline.util.DateUtil;
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

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final UserFactory userFactory;

    public AuthenticationService(UserRepository u, TokenUtils t, AuthenticationManager a, UserDetailsService ud, UserService userService, UserFactory userFactory) {
        this.userRepository = u;
        this.tokenUtils = t;
        this.authenticationManager = a;
        this.userDetailsService = ud;
        this.userService = userService;
        this.userFactory = userFactory;
    }

    /**
     * Authenticates the user in the SecurityContext
     *
     * @param request Username & Password
     * @return AuthenticationResponse Token & Authorities
     * @throws AuthenticationException if login data is invalid
     */
    public AuthenticationDTO loginUser(AuthenticationRequestDTO request) throws AuthenticationException, NoObjectForIdException {
        String username = request.getUsername();
        // Perform authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        String token = this.tokenUtils.generateToken(userDetails);
        return new AuthenticationDTO(
                token,
                userFactory.toUserDTO(userService.getUserByName(username))
        );
    }

    /**
     * Returns a fresh token if the given one is valid
     *
     * @param token a JWT
     * @return AuthenticationResponse Token & Authorities
     * @throws AuthenticationException If the token is no longer valid
     */
    public AuthenticationDTO refreshToken(String token) throws NoObjectForIdException {
        SecurityUser user;

        try {
            user = (SecurityUser) this.userDetailsService.loadUserByUsername(
                    this.tokenUtils.getUsernameFromToken(token));
        } catch (AuthenticationException e) {
            throw invalidToken();
        }

        if (this.tokenUtils.canTokenBeRefreshed(token, user)) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return new AuthenticationDTO(
                    refreshedToken,
                    userFactory.toUserDTO(userService.getUserByName(user.getUsername()))
            );
        } else throw invalidToken();
    }

    public void logoutUser(String token) throws AuthenticationException {
        String username = this.tokenUtils.getUsernameFromToken(token);
        SecurityUser securityUser;

        try {
            securityUser = (SecurityUser) this.userDetailsService.loadUserByUsername(username);
            if (this.tokenUtils.isTokenValid(token, securityUser)) {
                DateUtil dateUtil = new DateUtil();
                User user = this.userRepository.findByUsername(username);
                user.setLastLogout(dateUtil.getCurrentDate());
                this.userRepository.save(user);
            } else throw invalidToken();
        } catch (AuthenticationException e) {
            throw invalidToken();
        }
    }

    private AuthenticationException invalidToken() {
        return new InvalidTokenException("The token you sent is not valid");
    }
}
