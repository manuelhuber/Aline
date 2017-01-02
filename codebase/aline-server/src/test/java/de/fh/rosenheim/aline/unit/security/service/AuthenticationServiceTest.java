package de.fh.rosenheim.aline.unit.security.service;

import de.fh.rosenheim.aline.model.dtos.authentication.AuthenticationRequest;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.UserRepository;
import de.fh.rosenheim.aline.security.service.AuthenticationService;
import de.fh.rosenheim.aline.security.utils.Authorities;
import de.fh.rosenheim.aline.security.utils.TokenUtils;
import de.fh.rosenheim.aline.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class AuthenticationServiceTest {

    private static final String USERNAME = "John";
    private static final String PASSWORD = "foorbar123";
    private AuthenticationService authenticationService;

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenUtils tokenUtils;
    private UserService userService;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        userDetailsService = mock(UserDetailsService.class);
        tokenUtils = mock(TokenUtils.class);
        userService = mock(UserService.class);
        authenticationService = new AuthenticationService(userRepository, tokenUtils, authenticationManager, userDetailsService, userService);
    }

    @Test
    public void loginUser() throws NoObjectForIdException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                USERNAME,
                PASSWORD,
                Arrays.asList(new SimpleGrantedAuthority(Authorities.EMPLOYEE), new SimpleGrantedAuthority(Authorities.TOP_DOG))
        );
        given(this.authenticationManager.authenticate(any())).willReturn(authentication);
        authenticationService.loginUser(generateRequest());
    }

    private AuthenticationRequest generateRequest() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(USERNAME);
        authenticationRequest.setPassword(PASSWORD);
        return authenticationRequest;
    }
}
