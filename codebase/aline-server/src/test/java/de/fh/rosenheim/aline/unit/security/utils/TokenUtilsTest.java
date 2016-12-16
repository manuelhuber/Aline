package de.fh.rosenheim.aline.unit.security.utils;

import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.security.utils.TokenUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TokenUtilsTest {

    private static final int ERROR_MARGIN_IN_MILLISECONDS = 1000;
    private static final long EXPIRATION_IN_SECONDS = 10000;
    private TokenUtils tokenUtils;
    private UserDetails userDetails;

    @Before
    public void setUp() {
        tokenUtils = new TokenUtils();
        ReflectionTestUtils.setField(tokenUtils, "secret", "foobar123");
        ReflectionTestUtils.setField(tokenUtils, "expiration", EXPIRATION_IN_SECONDS);
        userDetails = new SecurityUser("John", null, null, null, null, null);
    }

    @Test
    public void createTokenWithCorrectUsername() {
        String token = tokenUtils.generateToken(userDetails);
        assertThat(tokenUtils.getUsernameFromToken(token)).isEqualTo("John");
    }

    @Test
    public void createTokenWithCorrectCreationDate() {
        String token = tokenUtils.generateToken(userDetails);
        assertThat(areDatesWithinMargin(tokenUtils.getCreatedDateFromToken(token), new Date())).isTrue();
    }

    @Test
    public void createTokenWithCorrectExpirationDate() {
        String token = tokenUtils.generateToken(userDetails);
        Date expectedExpirationDate = new Date();
        expectedExpirationDate.setTime(expectedExpirationDate.getTime() + EXPIRATION_IN_SECONDS * 1000);
        assertThat(areDatesWithinMargin(tokenUtils.getExpirationDateFromToken(token), expectedExpirationDate)).isTrue();
    }

    @Test
    public void validateValidToken() {
        SecurityUser securityUser = new SecurityUser("John", null, null, new Date(), new Date(), null);
        String token = tokenUtils.generateToken(userDetails);
        assertThat(tokenUtils.isTokenValid(token, securityUser)).isTrue();
    }

    @Test
    public void validateTokenAfterLogout() {
        SecurityUser securityUser = new SecurityUser("John", null, null, new Date(System.currentTimeMillis() + 100), new Date(), null);
        String token = tokenUtils.generateToken(userDetails);
        assertThat(tokenUtils.isTokenValid(token, securityUser)).isFalse();
    }

    @Test
    public void validateTokenAfterPasswordChange() {
        SecurityUser securityUser = new SecurityUser("John", null, null, new Date(), new Date(System.currentTimeMillis() + 100), null);
        String token = tokenUtils.generateToken(userDetails);
        assertThat(tokenUtils.isTokenValid(token, securityUser)).isFalse();
    }

    @Test
    public void refreshToken() {
        String token = tokenUtils.generateToken(userDetails);
        String refreshedToken = tokenUtils.refreshToken(token);
        assertThat(tokenUtils.getUsernameFromToken(refreshedToken)).isEqualTo("John");
        assertThat(areDatesWithinMargin(tokenUtils.getCreatedDateFromToken(refreshedToken), new Date())).isTrue();
    }

    private boolean areDatesWithinMargin(Date one, Date two) {
        return Math.abs(one.getTime() - two.getTime()) < ERROR_MARGIN_IN_MILLISECONDS;
    }
}
