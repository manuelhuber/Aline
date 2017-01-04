package de.fh.rosenheim.aline.unit.model.dtos;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.dtos.booking.BookingDTO;
import de.fh.rosenheim.aline.model.dtos.booking.BookingFactory;
import de.fh.rosenheim.aline.model.dtos.booking.BookingSummaryDTO;
import de.fh.rosenheim.aline.model.dtos.booking.UserBookingDTO;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class BookingFactoryTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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

        BookingDTO dto = BookingFactory.toBookingDTO(booking);
        assertThat(dto).isEqualTo(BookingFactory.toBookingDTO(booking));
        assertThat(dto.hashCode()).isEqualTo(BookingFactory.toBookingDTO(booking).hashCode());

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

        UserBookingDTO dto = BookingFactory.toUserBookingDTO(booking);
        assertThat(dto).isEqualTo(BookingFactory.toUserBookingDTO(booking));
        assertThat(dto.hashCode()).isEqualTo(BookingFactory.toUserBookingDTO(booking).hashCode());

        assertThat(dto.getId()).isEqualTo((long) 12);
        assertThat(dto.getStatus()).isEqualTo(BookingStatus.REQUESTED);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getUpdated()).isEqualTo(updated);
        assertThat(dto.getSeminarId()).isEqualTo((long) 99);
        assertThat(dto.getSeminarCost()).isEqualTo(789);
        assertThat(dto.getSeminarName()).isEqualTo("Dat Seminar");
        assertThat(dto.getSeminarYear()).isEqualTo(2018);
    }

    @Test
    public void generateBookingSummaryDTOs() throws ParseException {
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
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
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
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
                .build();

        Seminar seminar3 = new Seminar();
        seminar3.setName("WASD City");
        seminar3.setId((long) 100);
        seminar3.setDates((Date[])
                Arrays.asList(
                        sdf.parse("12/1/2019")
                ).toArray());
        seminar3.setCostsPerParticipant(456);

        Booking booking3 = Booking.builder()
                .status(BookingStatus.REQUESTED)
                .id((long) 13)
                .seminar(seminar3)
                .created(sdf.parse("11/11/1111"))
                .updated(sdf.parse("12/12/1112"))
                .build();

        List<BookingSummaryDTO> dtos = BookingFactory.toBookingSummaryDTOs(Arrays.asList(booking1, booking2, booking3));
        assertThat(dtos).isEqualTo(BookingFactory.toBookingSummaryDTOs(Arrays.asList(booking1, booking2, booking3)));
        assertThat(dtos.hashCode())
                .isEqualTo(BookingFactory.toBookingSummaryDTOs(Arrays.asList(booking1, booking2, booking3)).hashCode());

        assertThat(dtos.size()).isEqualTo(2);
        Optional<BookingSummaryDTO> dto2018 = dtos.stream().filter(dto -> dto.getYear() == 2018).findAny();
        assertThat(dto2018.isPresent()).isTrue();
        assertThat(dto2018.get().getGrantedSpending()).isEqualTo(789);
        assertThat(dto2018.get().getPlannedSpending()).isEqualTo(789 + 123);
        assertThat(dto2018.get().getBookings().size()).isEqualTo(2);
        Optional<BookingSummaryDTO> dto2019 = dtos.stream().filter(dto -> dto.getYear() == 2019).findAny();
        assertThat(dto2019.isPresent()).isTrue();
        assertThat(dto2019.get().getGrantedSpending()).isEqualTo(0);
        assertThat(dto2019.get().getPlannedSpending()).isEqualTo(456);
        assertThat(dto2019.get().getBookings().size()).isEqualTo(1);
    }
}
