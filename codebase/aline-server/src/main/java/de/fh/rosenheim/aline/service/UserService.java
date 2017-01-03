package de.fh.rosenheim.aline.service;

import com.google.common.collect.Lists;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByName(String name) throws NoObjectForIdException {
        User user = userRepository.findByUsername(name);
        if (user == null) {
            throw new NoObjectForIdException(User.class, name);
        }
        return user;
    }

    public Iterable<User> getUsersForDivision(String division) {
        return userRepository.findByDivision(division);
    }

    public List<String> getAllUserNames() {
        return Lists.newArrayList(userRepository.findAll()).stream().map(User::getUsername).collect(Collectors.toList());
    }
}
