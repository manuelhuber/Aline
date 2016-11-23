package de.fh.rosenheim.aline.util;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtil {

    @Value("${token.header}")
    private String tokenHeader;

    /**
     * Gets the token from the request header
     *
     * @param request HTTP request
     * @return Token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(this.tokenHeader);
    }
}
