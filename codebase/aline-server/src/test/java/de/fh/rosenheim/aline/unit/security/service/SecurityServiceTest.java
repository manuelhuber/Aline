package de.fh.rosenheim.aline.unit.security.service;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.security.service.SecurityService;
import de.fh.rosenheim.aline.security.utils.Authorities;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class SecurityServiceTest {

    private static final String USERNAME = "John";
    private static final String DIVISION = "FOO";
    private SecurityUser securityUser;
    private UserDetailsService userDetailsService;
    private SecurityService securityService;

    @Before
    public void setUp() {
        userDetailsService = mock(UserDetailsService.class);
        securityService = new SecurityService(userDetailsService);
    }

    @Before
    public void mockSecurityContext() {
        securityUser = new SecurityUser(USERNAME, null, DIVISION, null, null, generateAuthorities(Authorities.EMPLOYEE));
        Authentication auth = new UsernamePasswordAuthenticationToken(securityUser, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void canAccessUserDataAsDivisionHead() {
        SecurityUser principal = new SecurityUser("THE HEAD", null, DIVISION, null, null, generateAuthorities(Authorities.DIVISION_HEAD));
        UserDTO user = new UserDTO();
        user.setUserName(USERNAME);
        user.setDivision(DIVISION);
        assertThat(securityService.canAccessUserData(principal, user)).isTrue();
    }

    @Test
    public void canAccessUserDataForSelf() {
        SecurityUser principal = new SecurityUser(USERNAME, null, null, null, null, generateAuthorities(Authorities.EMPLOYEE));
        UserDTO user = new UserDTO();
        user.setUserName(USERNAME);
        user.setDivision(DIVISION);
        assertThat(securityService.canAccessUserData(principal, user)).isTrue();
    }

    @Test
    public void canAccessUserDataAsFrontOffice() {
        SecurityUser principal = new SecurityUser("FRONT", null, "front division", null, null, generateAuthorities(Authorities.FRONT_OFFICE));
        UserDTO user = new UserDTO();
        user.setUserName(USERNAME);
        user.setDivision(DIVISION);
        assertThat(securityService.canAccessUserData(principal, user)).isTrue();
    }

    @Test
    public void canAccessUserDataWithInsufficientRights() {
        SecurityUser principal = new SecurityUser("Dude", null, DIVISION, null, null, generateAuthorities(Authorities.EMPLOYEE));
        UserDTO user = new UserDTO();
        user.setUserName(USERNAME);
        user.setDivision(DIVISION);
        assertThat(securityService.canAccessUserData(principal, user)).isFalse();
    }

    @Test
    public void canBookForSelf() {
        SecurityUser principal = new SecurityUser(USERNAME, null, DIVISION, null, null, generateAuthorities(Authorities.EMPLOYEE));
        assertThat(securityService.canBookForUser(principal, null)).isTrue();
        assertThat(securityService.canBookForUser(principal, USERNAME)).isTrue();
    }

    @Test
    public void canBookAsFrontOffice() {
        SecurityUser principal = new SecurityUser("FRONT", null, "front division", null, null, generateAuthorities(Authorities.FRONT_OFFICE));
        assertThat(securityService.canBookForUser(principal, "Whomever")).isTrue();
    }

    @Test
    public void canBookForUserWithInsufficientRights() {
        SecurityUser principal = new SecurityUser("FRONT", null, "front division", null, null, generateAuthorities(Authorities.EMPLOYEE));
        assertThat(securityService.canBookForUser(principal, "Whomever")).isFalse();
    }

    @Test
    public void canCurrentUserChangeBookingStatusAsDivisionHead() {
        SecurityUser currentUser = new SecurityUser("HEAD", null, DIVISION, null, null, generateAuthorities(Authorities.DIVISION_HEAD));
        given(userDetailsService.loadUserByUsername(any())).willReturn(currentUser);
        Booking booking = new Booking();
        booking.setUser(User.builder().division(DIVISION).build());
        assertThat(securityService.canCurrentUserChangeBookingStatus(booking)).isTrue();
    }

    @Test
    public void canCurrentUserChangeBookingStatusAsFrontOffice() {
        SecurityUser currentUser = new SecurityUser("FRONT", null, "front division", null, null, generateAuthorities(Authorities.FRONT_OFFICE));
        given(userDetailsService.loadUserByUsername(any())).willReturn(currentUser);
        Booking booking = new Booking();
        booking.setUser(User.builder().division(DIVISION).build());
        assertThat(securityService.canCurrentUserChangeBookingStatus(booking)).isTrue();
    }

    @Test
    public void canCurrentUserChangeBookingStatusForSelf() {
        SecurityUser currentUser = new SecurityUser(USERNAME, null, "front division", null, null, generateAuthorities(Authorities.EMPLOYEE));
        given(userDetailsService.loadUserByUsername(any())).willReturn(currentUser);
        Booking booking = new Booking();
        booking.setUser(User.builder().division(DIVISION).build());
        assertThat(securityService.canCurrentUserChangeBookingStatus(booking)).isFalse();
    }

    @Test
    public void canCurrentUserDeleteBookingForSelf() {
        SecurityUser currentUser = new SecurityUser(USERNAME, null, DIVISION, null, null, generateAuthorities(Authorities.DIVISION_HEAD));
        given(userDetailsService.loadUserByUsername(any())).willReturn(currentUser);
        Booking booking = new Booking();
        booking.setUser(User.builder().division(DIVISION).username(USERNAME).build());
        assertThat(securityService.canCurrentUserDeleteBooking(booking)).isTrue();
    }

    @Test
    public void canCurrentUserDeleteBookingAsFrontOffice() {
        SecurityUser currentUser = new SecurityUser("FRONT", null, "front division", null, null, generateAuthorities(Authorities.FRONT_OFFICE));
        given(userDetailsService.loadUserByUsername(any())).willReturn(currentUser);
        Booking booking = new Booking();
        booking.setUser(User.builder().division(DIVISION).build());
        assertThat(securityService.canCurrentUserDeleteBooking(booking)).isTrue();
    }

    @Test
    public void canCurrentUserDeleteBookingAsDivisionHead() {
        SecurityUser currentUser = new SecurityUser("HEAD", null, DIVISION, null, null, generateAuthorities(Authorities.DIVISION_HEAD));
        given(userDetailsService.loadUserByUsername(any())).willReturn(currentUser);
        Booking booking = new Booking();
        booking.setUser(User.builder().division(DIVISION).build());
        assertThat(securityService.canCurrentUserDeleteBooking(booking)).isFalse();
    }

    @Test
    public void canGetDivisionUsersAsDivisionHead() {
        SecurityUser currentUser = new SecurityUser("HEAD", null, DIVISION, null, null, generateAuthorities(Authorities.DIVISION_HEAD));
        assertThat(securityService.canGetDivisionUsers(currentUser, null)).isTrue();
        assertThat(securityService.canGetDivisionUsers(currentUser, DIVISION)).isTrue();
        assertThat(securityService.canGetDivisionUsers(currentUser, "OTHERDIVISION")).isFalse();
    }

    @Test
    public void canGetDivisionUsersAsFrontOffice() {
        SecurityUser currentUser = new SecurityUser("FRONT", null, DIVISION, null, null, generateAuthorities(Authorities.FRONT_OFFICE));
        assertThat(securityService.canGetDivisionUsers(currentUser, null)).isTrue();
        assertThat(securityService.canGetDivisionUsers(currentUser, DIVISION)).isTrue();
        assertThat(securityService.canGetDivisionUsers(currentUser, "SOMETHING")).isTrue();
    }

    @Test
    public void canGetDivisionUsersWithInsufficientRights() {
        SecurityUser currentUser = new SecurityUser("DUDE", null, DIVISION, null, null, generateAuthorities(Authorities.EMPLOYEE));
        assertThat(securityService.canGetDivisionUsers(currentUser, null)).isFalse();
        assertThat(securityService.canGetDivisionUsers(currentUser, DIVISION)).isFalse();
        assertThat(securityService.canGetDivisionUsers(currentUser, "SOMETHING")).isFalse();
    }

    private Collection<SimpleGrantedAuthority> generateAuthorities(String... authorityNames) {
        Collection<SimpleGrantedAuthority> authorities = Arrays.stream(authorityNames)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }
}
