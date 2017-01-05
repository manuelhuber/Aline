package de.fh.rosenheim.aline.unit.security.service;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.authentication.AuthenticationDTO;
import de.fh.rosenheim.aline.model.dtos.authentication.AuthenticationRequestDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import de.fh.rosenheim.aline.model.exceptions.InvalidTokenException;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.repository.UserRepository;
import de.fh.rosenheim.aline.security.service.AuthenticationService;
import de.fh.rosenheim.aline.security.utils.Authorities;
import de.fh.rosenheim.aline.security.utils.TokenUtils;
import de.fh.rosenheim.aline.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    private static final String USERNAME = "John";
    private static final String PASSWORD = "foobar123";
    private AuthenticationService authenticationService;

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenUtils tokenUtils;
    private UserService userService;
    private UserFactory userFactory;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        userDetailsService = mock(UserDetailsService.class);
        tokenUtils = mock(TokenUtils.class);
        userService = mock(UserService.class);
        userFactory = mock(UserFactory.class);
        authenticationService = new AuthenticationService(userRepository, tokenUtils, authenticationManager, userDetailsService, userService, userFactory);
    }

    @Test
    public void loginUser() throws NoObjectForIdException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                USERNAME,
                PASSWORD,
                Arrays.asList(new SimpleGrantedAuthority(Authorities.EMPLOYEE), new SimpleGrantedAuthority(Authorities.TOP_DOG))
        );
        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(userService.getUserByName(any())).willReturn(User.builder().username(USERNAME).build());
        given(tokenUtils.generateToken(any())).willReturn("thetoken");
        UserDTO user = new UserDTO();
        user.setUserName(USERNAME);
        given(userFactory.toUserDTO(any())).willReturn(user);

        AuthenticationDTO authenticationDTO = authenticationService.loginUser(generateRequest());
        assertThat(authenticationDTO.getUser().getUserName()).isEqualTo(USERNAME);
        assertThat(authenticationDTO.getToken()).isEqualTo("thetoken");
    }

    @Test
    public void refreshTokenWhenUserCanNotBeLoaded() throws NoObjectForIdException {
        exception.expect(InvalidTokenException.class);
        given(userDetailsService.loadUserByUsername(any())).willThrow(UsernameNotFoundException.class);
        authenticationService.refreshToken("foobarfoobarfoobarfoobar");
    }

    @Test
    public void refreshUnrefreshableToken() throws NoObjectForIdException {
        exception.expect(InvalidTokenException.class);
        given(tokenUtils.canTokenBeRefreshed(any(), any())).willReturn(false);
        authenticationService.refreshToken("foobarfoobarfoobarfoobar");
    }

    @Test
    public void refreshToken() throws NoObjectForIdException {
        given(tokenUtils.canTokenBeRefreshed(any(), any())).willReturn(true);
        given(tokenUtils.refreshToken(any())).willReturn("freshToken");
        given(userService.getUserByName(any())).willReturn(User.builder().username(USERNAME).build());
        SecurityUser securityUser = new SecurityUser("John", null, null, null, null, null);
        given(userDetailsService.loadUserByUsername(any())).willReturn(securityUser);
        UserDTO user = new UserDTO();
        user.setUserName(USERNAME);
        given(userFactory.toUserDTO(any())).willReturn(user);

        AuthenticationDTO fresh = authenticationService.refreshToken("foobarfoobarfoobarfoobar");
        assertThat(fresh.getUser().getUserName()).isEqualTo(USERNAME);
        assertThat(fresh.getToken()).isEqualTo("freshToken");
    }

    @Test
    public void logoutUserWhenUserCanNotBeLoaded() throws NoObjectForIdException {
        exception.expect(InvalidTokenException.class);
        given(userDetailsService.loadUserByUsername(any())).willThrow(UsernameNotFoundException.class);
        authenticationService.logoutUser("foobarfoobarfoobarfoobar");
    }

    @Test
    public void logoutUserWithInvalidToken() throws NoObjectForIdException {
        exception.expect(InvalidTokenException.class);
        given(tokenUtils.isTokenValid(any(), any())).willReturn(false);
        authenticationService.logoutUser("foobarfoobarfoobarfoobar");
    }

    @Test
    public void logoutUser() throws NoObjectForIdException {
        User user = new User();
        user.setUsername("Danny");
        given(userRepository.findByUsername(any())).willReturn(user);
        given(tokenUtils.isTokenValid(any(), any())).willReturn(true);

        assertThat(user.getLastLogout()).isNull();
        authenticationService.logoutUser("foobarfoobarfoobarfoobar");
        assertThat(user.getLastLogout()).isNotNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    private AuthenticationRequestDTO generateRequest() {
        AuthenticationRequestDTO authenticationRequest = new AuthenticationRequestDTO();
        authenticationRequest.setUsername(USERNAME);
        authenticationRequest.setPassword(PASSWORD);
        return authenticationRequest;
    }
}
