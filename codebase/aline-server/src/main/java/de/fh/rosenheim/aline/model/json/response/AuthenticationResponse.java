package de.fh.rosenheim.aline.model.json.response;

import de.fh.rosenheim.aline.model.base.ModelBase;

public class AuthenticationResponse extends ModelBase {

    private static final long serialVersionUID = -6624726180748515507L;
    private String token;
    private String[] authorities;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(String token, String[] authorities) {
        this.token = token;
        this.authorities = authorities;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }
}
