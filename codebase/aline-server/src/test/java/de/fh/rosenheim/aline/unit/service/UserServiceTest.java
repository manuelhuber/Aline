package de.fh.rosenheim.aline.unit.service;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.repository.UserRepository;
import de.fh.rosenheim.aline.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void createService() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void getNonExistingUser() throws NoObjectForIdException {
        exception.expect(NoObjectForIdException.class);
        given(userService.getUserByName("Foo")).willReturn(null);
        userService.getUserByName("Foo");
    }

    @Test
    public void getUser() throws NoObjectForIdException {
        User user = new User();
        given(userRepository.findByUsername("Foo")).willReturn(user);
        assertThat(userService.getUserByName("Foo")).isEqualTo(user);
    }

    @Test
    public void getUserNames() {
        User user1 = new User();
        user1.setUsername("John");
        User user2 = new User();
        user2.setUsername("Pete");
        User user3 = new User();
        user3.setUsername("Micky");

        final LinkedList<User> list = new LinkedList<>(Arrays.asList(user1, user2, user3));
        given(userRepository.findAll()).willReturn(list);
        assertThat(userService.getAllUsernames())
                .contains("John")
                .contains("Pete")
                .contains("Micky")
                .hasSize(3);
    }

    @Test
    public void getUsersForDivision() {
        User user1 = new User();
        user1.setUsername("John");
        User user2 = new User();
        user2.setUsername("Pete");
        User user3 = new User();
        user3.setUsername("Micky");

        final LinkedList<User> list = new LinkedList<>(Arrays.asList(user1, user2, user3));
        given(userRepository.findByDivision("Foo")).willReturn(list);
        assertThat(userService.getUsersForDivision("Foo"))
                .contains(user1)
                .contains(user2)
                .contains(user3)
                .hasSize(3);
    }
}
