package de.fh.rosenheim.aline.unit.security.service;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.repository.UserRepository;
import de.fh.rosenheim.aline.security.service.UserDetailsServiceImpl;
import de.fh.rosenheim.aline.security.utils.Authorities;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class UserDetailsServiceImplTest {

    private static final String USERNAME = "JohnDoe";
    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void getUser() {
        Date passwordReset = new Date(System.currentTimeMillis() - 1000);
        Date logout = new Date(System.currentTimeMillis() + 1000);
        User user = User.builder()
                .username(USERNAME)
                .firstName("John")
                .lastName("Doe")
                .division("FOO")
                .lastPasswordReset(passwordReset)
                .lastLogout(logout)
                .password("foobar1234")
                .authorities(Authorities.EMPLOYEE)
                .build();
        given(userRepository.findByUsername(USERNAME)).willReturn(user);
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(USERNAME);
        assertThat(securityUser.getUsername()).isEqualTo(USERNAME);
        assertThat(securityUser.getDivision()).isEqualTo("FOO");
        assertThat(securityUser.getPassword()).isEqualTo("foobar1234");
        assertThat(securityUser.getLastLogout()).isEqualTo(logout);
        assertThat(securityUser.getLastPasswordReset()).isEqualTo(passwordReset);
    }
}
