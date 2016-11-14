package de.fh.rosenheim.service;

import de.fh.rosenheim.domain.entity.User;
import de.fh.rosenheim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void logout(String username) {
        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            user.setLastLogout(new Date());
            this.userRepository.save(user);
        }
    }
}
