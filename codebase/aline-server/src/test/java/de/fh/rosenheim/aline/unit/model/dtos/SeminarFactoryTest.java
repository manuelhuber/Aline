package de.fh.rosenheim.aline.unit.model.dtos;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarBasicsDTO;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarDTO;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarFactory;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Generates and transforms all seminar models & DTOs
 */
@Component
public class SeminarFactoryTest {

    @Test
    public void toSeminarDTOTest() {
        Seminar seminar = new Seminar();
        seminar.setId(seminar.getId());
        seminar.setName(seminar.getName());
        seminar.setDescription(seminar.getDescription());
        seminar.setTrainer(seminar.getTrainer());
        seminar.setAgenda(seminar.getAgenda());
        seminar.setBookable(seminar.isBookable());
        seminar.setCategory(seminar.getCategory());
        seminar.setTargetLevel(seminar.getTargetLevel());
        seminar.setRequirements(seminar.getRequirements());
        seminar.setContactPerson(seminar.getContactPerson());
        seminar.setTrainingType(seminar.getTrainingType());
        seminar.setMaximumParticipants(seminar.getMaximumParticipants());
        seminar.setCostsPerParticipant(seminar.getCostsPerParticipant());
        seminar.setBookingTimelog(seminar.getBookingTimelog());
        seminar.setGoal(seminar.getGoal());
        seminar.setDuration(seminar.getDuration());
        seminar.setCycle(seminar.getCycle());
        seminar.setDates(seminar.getDates());
        seminar.setBillGenerated(seminar.isBillGenerated());

        Date date1 = new Date();
        Date created = new Date();
        Date updated = new Date();

        Booking booking1 = Booking.builder().status(BookingStatus.DENIED).build();
        Booking booking2 = Booking.builder().status(BookingStatus.REQUESTED).build();
        Booking booking3 = Booking.builder().status(BookingStatus.GRANTED).build();
        Set<Booking> bookings = new HashSet<>();
        bookings.add(booking1);
        bookings.add(booking2);
        bookings.add(booking3);

        seminar.setId((long) 12);
        seminar.setBookings(bookings);
        seminar.setName("name");
        seminar.setDescription("description");
        seminar.setAgenda("agenda");
        seminar.setBookable(true);
        seminar.setCategory("CATEGORY");
        seminar.setTargetLevel(new int[]{1, 2, 3});
        seminar.setRequirements("requirements");
        seminar.setTrainer("trainer");
        seminar.setContactPerson("contact");
        seminar.setTrainingType("type");
        seminar.setMaximumParticipants(12);
        seminar.setCostsPerParticipant(789);
        seminar.setBookingTimelog("timelog");
        seminar.setGoal("goal");
        seminar.setDuration("duration");
        seminar.setCycle("cycle");
        seminar.setDates(new Date[]{date1});
        seminar.setBillGenerated(false);
        seminar.setCreated(created);
        seminar.setUpdated(updated);

        SeminarDTO dto = SeminarFactory.toSeminarDTO(seminar);
        assertThat(dto).isEqualTo(SeminarFactory.toSeminarDTO(seminar));
        assertThat(dto.hashCode()).isEqualTo(SeminarFactory.toSeminarDTO(seminar).hashCode());

        assertThat(dto.getId()).isEqualTo((long) 12);
        assertThat(dto.getActiveBookings()).isEqualTo(2);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getUpdated()).isEqualTo(updated);
        assertThat(dto.isBillGenerated()).isEqualTo(false);
        assertThat(dto.getName()).isEqualTo("name");
        assertThat(dto.getDescription()).isEqualTo("description");
        assertThat(dto.getAgenda()).isEqualTo("agenda");
        assertThat(dto.isBookable()).isEqualTo(true);
        assertThat(dto.getCategory()).isEqualTo("CATEGORY");
        assertThat(dto.getTargetLevel()).contains(1, 2, 3).hasSize(3);
        assertThat(dto.getRequirements()).isEqualTo("requirements");
        assertThat(dto.getTrainer()).isEqualTo("trainer");
        assertThat(dto.getContactPerson()).isEqualTo("contact");
        assertThat(dto.getTrainingType()).isEqualTo("type");
        assertThat(dto.getMaximumParticipants()).isEqualTo(12);
        assertThat(dto.getCostsPerParticipant()).isEqualTo(789);
        assertThat(dto.getBookingTimelog()).isEqualTo("timelog");
        assertThat(dto.getGoal()).isEqualTo("goal");
        assertThat(dto.getDuration()).isEqualTo("duration");
        assertThat(dto.getCycle()).isEqualTo("cycle");
        assertThat(dto.getDates()).hasSize(1).contains(date1);
    }

    @Test
    public void createSeminarTest() {
        SeminarBasicsDTO basicsDTO = new SeminarBasicsDTO();
        Date date1 = new Date();

        basicsDTO.setName("name");
        basicsDTO.setDescription("description");
        basicsDTO.setAgenda("agenda");
        basicsDTO.setBookable(true);
        basicsDTO.setCategory("CATEGORY");
        basicsDTO.setTargetLevel(new int[]{1, 2, 3});
        basicsDTO.setRequirements("requirements");
        basicsDTO.setTrainer("trainer");
        basicsDTO.setContactPerson("contact");
        basicsDTO.setTrainingType("type");
        basicsDTO.setMaximumParticipants(12);
        basicsDTO.setCostsPerParticipant(789);
        basicsDTO.setBookingTimelog("timelog");
        basicsDTO.setGoal("goal");
        basicsDTO.setDuration("duration");
        basicsDTO.setCycle("cycle");
        basicsDTO.setDates(new Date[]{date1});

        Seminar seminar = SeminarFactory.createSeminar(basicsDTO);
        assertThat(seminar).isEqualTo(SeminarFactory.createSeminar(basicsDTO));
        assertThat(seminar.hashCode()).isEqualTo(SeminarFactory.createSeminar(basicsDTO).hashCode());

        assertThat(seminar.getName()).isEqualTo("name");
        assertThat(seminar.getDescription()).isEqualTo("description");
        assertThat(seminar.getAgenda()).isEqualTo("agenda");
        assertThat(seminar.isBookable()).isEqualTo(true);
        assertThat(seminar.getCategory()).isEqualTo("CATEGORY");
        assertThat(seminar.getTargetLevel()).contains(1, 2, 3).hasSize(3);
        assertThat(seminar.getRequirements()).isEqualTo("requirements");
        assertThat(seminar.getTrainer()).isEqualTo("trainer");
        assertThat(seminar.getContactPerson()).isEqualTo("contact");
        assertThat(seminar.getTrainingType()).isEqualTo("type");
        assertThat(seminar.getMaximumParticipants()).isEqualTo(12);
        assertThat(seminar.getCostsPerParticipant()).isEqualTo(789);
        assertThat(seminar.getBookingTimelog()).isEqualTo("timelog");
        assertThat(seminar.getGoal()).isEqualTo("goal");
        assertThat(seminar.getDuration()).isEqualTo("duration");
        assertThat(seminar.getCycle()).isEqualTo("cycle");
        assertThat(seminar.getDates()).hasSize(1).contains(date1);
    }
}
