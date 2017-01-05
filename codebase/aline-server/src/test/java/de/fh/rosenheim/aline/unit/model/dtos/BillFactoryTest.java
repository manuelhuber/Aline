package de.fh.rosenheim.aline.unit.model.dtos;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.dtos.bill.BillDTO;
import de.fh.rosenheim.aline.model.dtos.bill.BillFactory;
import de.fh.rosenheim.aline.model.dtos.bill.DivisionSumDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class BillFactoryTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private BillFactory billFactory;
    private UserFactory userFactory;

    @Before
    public void setUp() {
        userFactory = mock(UserFactory.class);
        billFactory = new BillFactory(userFactory);
    }

    @Test
    public void generateBillTest() throws ParseException {
        Seminar seminar = new Seminar();
        seminar.setName("Dat Seminar");
        seminar.setId((long) 99);
        seminar.setDates((Date[])
                Arrays.asList(
                        sdf.parse("12/1/2015"),
                        sdf.parse("12/1/2018"),
                        sdf.parse("12/1/2017")
                ).toArray());
        seminar.setCostsPerParticipant(789);

        User user1 = User.builder()
                .division("FOO")
                .build();
        Booking booking1 = Booking.builder()
                .status(BookingStatus.GRANTED)
                .id((long) 11)
                .seminar(seminar)
                .user(user1)
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
                .build();

        User user2 = User.builder()
                .division("FOO")
                .build();
        Booking booking2 = Booking.builder()
                .status(BookingStatus.GRANTED)
                .id((long) 12)
                .seminar(seminar)
                .user(user2)
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
                .build();

        User user3 = User.builder()
                .division("BAR")
                .build();
        Booking booking3 = Booking.builder()
                .status(BookingStatus.GRANTED)
                .id((long) 13)
                .seminar(seminar)
                .user(user3)
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
                .build();

        User user4 = User.builder()
                .division("BAR")
                .build();
        Booking booking4 = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 14)
                .seminar(seminar)
                .user(user4)
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
                .build();

        User user5 = User.builder()
                .division("BAR")
                .build();
        Booking booking5 = Booking.builder()
                .status(BookingStatus.DENIED)
                .id((long) 15)
                .seminar(seminar)
                .user(user5)
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
                .build();

        Set<Booking> bookings = new HashSet<>();
        bookings.add(booking1);
        bookings.add(booking2);
        bookings.add(booking3);
        bookings.add(booking4);
        bookings.add(booking5);
        seminar.setBookings(bookings);

        given(userFactory.toUserDTO(any())).willAnswer(invocation -> {
            User user = (User) invocation.getArguments()[0];
            UserDTO dto = new UserDTO();
            dto.setDivision(user.getDivision());
            return dto;
        });

        BillDTO bill = billFactory.generateBill(seminar);
        assertThat(bill).isEqualTo(billFactory.generateBill(seminar));
        assertThat(bill.hashCode()).isEqualTo(billFactory.generateBill(seminar).hashCode());

        assertThat(bill.getParticipantCount()).isEqualTo(3);
        assertThat(bill.getParticipants()).hasSize(3);
        assertThat(bill.getTotalCost()).isEqualTo(3 * 789);
        assertThat(bill.getDivisionSums()).hasSize(2);
        Optional<DivisionSumDTO> bar
                = bill.getDivisionSums().stream().filter(sum -> sum.getDivision().equals("BAR")).findAny();
        Optional<DivisionSumDTO> foo
                = bill.getDivisionSums().stream().filter(sum -> sum.getDivision().equals("FOO")).findAny();

        assertThat(bar.isPresent()).isTrue();
        assertThat(foo.isPresent()).isTrue();
        assertThat(bar.get().getTotalCost()).isEqualTo((long) 789);
        assertThat(foo.get().getTotalCost()).isEqualTo((long) 2 * 789);
    }
}
