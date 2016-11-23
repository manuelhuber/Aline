package de.fh.rosenheim.aline.util;

import de.fh.rosenheim.aline.security.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ControllerUtil {

    @Value("${token.header}")
    private String tokenHeader;
    private final TokenUtils tokenUtils;

    public ControllerUtil(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    /**
     * Returns the token from the request header
     *
     * @param request HTTP request
     * @return Token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(this.tokenHeader);
    }

    /**
     * Returns the user from the token in the request header
     *
     * @param request HTTP request
     * @return username
     */
    public String getUsername(HttpServletRequest request) {
        return tokenUtils.getUsernameFromToken(getToken(request));
    }
}
