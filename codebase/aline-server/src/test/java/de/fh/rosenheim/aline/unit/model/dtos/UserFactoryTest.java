package de.fh.rosenheim.aline.unit.model.dtos;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserFactoryTest {

    private UserFactory userFactory;
    private BookingFactory bookingFactory;

    @Before
    public void setUp() {
        bookingFactory = mock(BookingFactory.class);
        userFactory = new UserFactory(bookingFactory);
    }

    @Test
    public void toUserDTOTest() {
        User user = User.builder()
                .username("Danny")
                .authorities("TOP_DOG,DIVISION_HEAD")
                .division("FOO")
                .firstName("John")
                .lastName("Doe")
                .build();
        UserDTO dto = userFactory.toUserDTO(user);
        assertThat(dto).isEqualTo(userFactory.toUserDTO(user));
        assertThat(dto.hashCode()).isEqualTo(userFactory.toUserDTO(user).hashCode());

        assertThat(dto.getUserName()).isEqualTo("Danny");
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.getDivision()).isEqualTo("FOO");
        assertThat(dto.getAuthorities())
                .hasSize(2)
                .contains("TOP_DOG")
                .contains("DIVISION_HEAD");
    }
}
