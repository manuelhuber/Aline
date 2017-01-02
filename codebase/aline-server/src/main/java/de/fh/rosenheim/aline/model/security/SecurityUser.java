package de.fh.rosenheim.aline.model.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Implementation of UserDetails, which is required for Spring Security
 */
@Data
public class SecurityUser implements UserDetails {

    private String username;
    private String password;
    private String division;
    private Date lastPasswordReset;
    private Date lastLogout;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;

    public SecurityUser(String username,
                        String password,
                        String division,
                        Date lastPasswordReset,
                        Date lastLogout,
                        Collection<? extends GrantedAuthority> authorities) {
        this.setUsername(username);
        this.setPassword(password);
        this.setDivision(division);
        this.setLastPasswordReset(lastPasswordReset);
        this.setLastLogout(lastLogout);
        this.setAuthorities(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }
}
