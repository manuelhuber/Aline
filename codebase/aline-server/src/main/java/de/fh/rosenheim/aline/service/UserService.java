package de.fh.rosenheim.aline.service;

import de.fh.rosenheim.aline.domain.entity.User;
import de.fh.rosenheim.aline.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
