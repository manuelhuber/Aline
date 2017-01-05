package de.fh.rosenheim.aline.unit.model.dtos;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.dtos.booking.BookingDTO;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.booking.BookingSummaryDTO;
import de.fh.rosenheim.aline.model.dtos.booking.UserBookingDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import de.fh.rosenheim.aline.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BookingFactoryTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private BookingFactory bookingFactory;
    private UserFactory userFactory;
    private DateUtil dateUtil;

    @Before
    public void setUp() {
        dateUtil = mock(DateUtil.class);
        userFactory = mock(UserFactory.class);
        bookingFactory = new BookingFactory(dateUtil, userFactory);
    }

    @Test
    public void toBookingDTOTest() throws ParseException {
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

        Date created = sdf.parse("11/11/1111");
        Date updated = sdf.parse("12/12/1112");

        Booking booking = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 12)
                .seminar(seminar)
                .created(created)
                .updated(updated)
                .build();

        BookingDTO dto = bookingFactory.toBookingDTO(booking);
        assertThat(dto).isEqualTo(bookingFactory.toBookingDTO(booking));
        assertThat(dto.hashCode()).isEqualTo(bookingFactory.toBookingDTO(booking).hashCode());

        assertThat(dto.getId()).isEqualTo(12);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getUpdated()).isEqualTo(updated);
        assertThat(dto.getStatus()).isEqualTo(BookingStatus.REQUESTED);
    }

    @Test
    public void generateUserBooking() throws ParseException {
        Seminar seminar = new Seminar();
        seminar.setName("Dat Seminar");
        seminar.setId((long) 99);
        seminar.setDates((Date[])
                Arrays.asList(
                        sdf.parse("12/1/2015"),
                        sdf.parse("12/10/2018"),
                        sdf.parse("12/1/2017")
                ).toArray());
        seminar.setCostsPerParticipant(789);

        Date created = sdf.parse("11/11/1111");
        Date updated = sdf.parse("12/12/1112");

        Booking booking = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 12)
                .seminar(seminar)
                .created(created)
                .updated(updated)
                .build();

        Seminar seminar2 = new Seminar();
        seminar2.setName("Dat Seminar");
        seminar2.setId((long) 100);
        seminar2.setDates((Date[])
                Arrays.asList(
                        sdf.parse("1/6/2018")
                ).toArray());
        seminar2.setCostsPerParticipant(789);

        Booking booking2 = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 12)
                .seminar(seminar2)
                .created(created)
                .updated(updated)
                .build();

        Date today = sdf.parse("10/6/2018");
        given(dateUtil.getCurrentDate()).willReturn(today);

        UserBookingDTO dto = bookingFactory.toUserBookingDTO(booking);
        assertThat(dto).isEqualTo(bookingFactory.toUserBookingDTO(booking));
        assertThat(dto.hashCode()).isEqualTo(bookingFactory.toUserBookingDTO(booking).hashCode());

        assertThat(dto.getId()).isEqualTo((long) 12);
        assertThat(dto.getStatus()).isEqualTo(BookingStatus.REQUESTED);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getUpdated()).isEqualTo(updated);
        assertThat(dto.getSeminarId()).isEqualTo((long) 99);
        assertThat(dto.getSeminarCost()).isEqualTo(789);
        assertThat(dto.getSeminarName()).isEqualTo("Dat Seminar");
        assertThat(dto.getSeminarYear()).isEqualTo(2018);
        assertThat(dto.isSeminarOver()).isFalse();

        assertThat(bookingFactory.toUserBookingDTO(booking2).isSeminarOver()).isTrue();
    }

    @Test
    public void generateBookingSummaryDTOs() throws ParseException {

        Date past = sdf.parse("1/7/2019");
        Date today = sdf.parse("11/7/2019");
        Date future = sdf.parse("21/7/2019");

        given(dateUtil.getCurrentDate()).willReturn(today);

        Seminar seminar1 = new Seminar();
        seminar1.setName("Dat Seminar");
        seminar1.setId((long) 99);
        seminar1.setDates((Date[])
                Arrays.asList(
                        sdf.parse("12/1/2015"),
                        sdf.parse("12/1/2018"),
                        sdf.parse("12/1/2017")
                ).toArray());
        seminar1.setCostsPerParticipant(789);

        Booking booking1 = Booking.builder()
                .status(BookingStatus.GRANTED)
                .id((long) 12)
                .seminar(seminar1)
                .build();

        Seminar seminar2 = new Seminar();
        seminar2.setName("Them foobars");
        seminar2.setId((long) 100);
        seminar2.setDates((Date[])
                Arrays.asList(
                        sdf.parse("12/8/2018"),
                        sdf.parse("12/1/2018")
                ).toArray());
        seminar2.setCostsPerParticipant(123);

        Booking booking2 = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 13)
                .seminar(seminar2)
                .build();

        int costPast = 3;
        int costFuture = 5;

        Seminar seminar3 = new Seminar();
        seminar3.setName("WASD City");
        seminar3.setId((long) 100);
        seminar3.setDates((Date[]) Arrays.asList(past).toArray());
        seminar3.setCostsPerParticipant(costPast);

        Seminar seminar4 = new Seminar();
        seminar4.setName("WASD City");
        seminar4.setId((long) 100);
        seminar4.setDates((Date[]) Arrays.asList(future).toArray());
        seminar4.setCostsPerParticipant(costFuture);

        Booking booking3 = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 13)
                .seminar(seminar3)
                .build();
        Booking booking4 = Booking.builder()
                .status(BookingStatus.DENIED)
                .id((long) 14)
                .seminar(seminar3)
                .build();
        Booking booking5 = Booking.builder()
                .status(BookingStatus.GRANTED)
                .id((long) 15)
                .seminar(seminar3)
                .build();

        Booking booking6 = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 16)
                .seminar(seminar4)
                .build();
        Booking booking7 = Booking.builder()
                .status(BookingStatus.DENIED)
                .id((long) 17)
                .seminar(seminar4)
                .build();
        Booking booking8 = Booking.builder()
                .status(BookingStatus.GRANTED)
                .id((long) 18)
                .seminar(seminar4)
                .build();

        List<BookingSummaryDTO> dtos = bookingFactory.toBookingSummaryDTOs(Arrays.asList(
                booking1, booking2, booking3, booking4, booking5, booking6, booking7, booking8));
        assertThat(dtos).isEqualTo(bookingFactory.toBookingSummaryDTOs(Arrays.asList(
                booking1, booking2, booking3, booking4, booking5, booking6, booking7, booking8)));
        assertThat(dtos.hashCode())
                .isEqualTo(bookingFactory.toBookingSummaryDTOs(Arrays.asList(
                        booking1, booking2, booking3, booking4, booking5, booking6, booking7, booking8)).hashCode());

        assertThat(dtos.size()).isEqualTo(2);

        BookingSummaryDTO dto2019 = dtos.get(0);
        assertThat(dto2019.getYear()).isEqualTo(2019);
        assertThat(dto2019.getPlannedTotalSpending()).isEqualTo(costPast + costFuture * 2);
        assertThat(dto2019.getPlannedAdditionalSpending()).isEqualTo(costFuture * 2);
        assertThat(dto2019.getGrantedSpending()).isEqualTo(costPast + costFuture);
        assertThat(dto2019.getIssuedSpending()).isEqualTo(costPast);
        assertThat(dto2019.getBookings().size()).isEqualTo(6);

        BookingSummaryDTO dto2018 = dtos.get(1);
        assertThat(dto2018.getYear()).isEqualTo(2018);
        assertThat(dto2018.getGrantedSpending()).isEqualTo(789);
        assertThat(dto2018.getPlannedTotalSpending()).isEqualTo(789);
        assertThat(dto2018.getBookings().size()).isEqualTo(2);
    }
}
