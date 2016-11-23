package de.fh.rosenheim.aline.service;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByName(String name) throws NoObjectForIdException {
        User user = this.userRepository.findByUsername(name);
        if (user == null) {
            throw new NoObjectForIdException(name);
        }
        return user;
    }
}
