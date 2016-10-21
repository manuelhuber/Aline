package de.fh.rosenheim.security;

import de.fh.rosenheim.model.Employee;
import de.fh.rosenheim.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Created by Manuel on 21.10.2016.
 */
@Service
public class FakeUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        Employee employee = employeeRepository.findByNameEquals(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return new User(username, "password", getGrantedAuthorities(username));
    }


    private Collection<? extends GrantedAuthority> getGrantedAuthorities(String
                                                                                 username) {
        Collection<? extends GrantedAuthority> authorities;
        if (username.equals("John")) {
            authorities = asList(() -> Roles.DIVISION_HEAD, () -> Roles.STAFF);
        } else {
            authorities = asList(() -> Roles.STAFF);
        }
        return authorities;
    }
}
