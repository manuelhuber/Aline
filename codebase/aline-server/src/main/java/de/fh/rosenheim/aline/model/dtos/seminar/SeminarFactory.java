package de.fh.rosenheim.aline.model.dtos.seminar;

import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;

/**
 * Created by Manuel on 02.01.2017.
 */
public class SeminarFactory {

    public static SeminarDTO toSeminarDTO(Seminar seminar) {
        SeminarDTO dto = new SeminarDTO();
        dto.setName(seminar.getName());
        dto.setDescription(seminar.getDescription());
        dto.setTrainer(seminar.getTrainer());
        dto.setAgenda(seminar.getAgenda());
        dto.setBookable(seminar.isBookable());
        dto.setCategory(seminar.getCategory());
        dto.setTargetLevel(seminar.getTargetLevel());
        dto.setRequirements(seminar.getRequirements());
        dto.setContactPerson(seminar.getContactPerson());
        dto.setTrainingType(seminar.getTrainingType());
        dto.setMaximumParticipants(seminar.getMaximumParticipants());
        dto.setCostsPerParticipant(seminar.getCostsPerParticipant());
        dto.setBookingTimelog(seminar.getBookingTimelog());
        dto.setGoal(seminar.getGoal());
        dto.setDuration(seminar.getDuration());
        dto.setCycle(seminar.getCycle());
        dto.setDates(seminar.getDates());

        dto.setActiveBookings(
                (int) seminar.getBookings()
                        .stream()
                        .filter(booking -> !booking.getStatus().equals(BookingStatus.DENIED))
                        .count()
        );

        dto.setCreated(seminar.getCreated());
        dto.setUpdated(seminar.getUpdated());
        dto.setId(seminar.getId());
        return dto;
    }

    public static Seminar createSeminar(SeminarBasicsDTO basicsDTO) {
        Seminar seminar = new Seminar();
        copyBasicDataToSeminar(seminar, basicsDTO);
        return seminar;
    }

    public static Seminar updateSeminar(Seminar seminar, SeminarBasicsDTO basicsDTO) {
        copyBasicDataToSeminar(seminar, basicsDTO);
        return seminar;
    }

    private static void copyBasicDataToSeminar(Seminar seminar, SeminarBasicsDTO basicsDTO) {
        seminar.setName(basicsDTO.getName());
        seminar.setDescription(basicsDTO.getDescription());
        seminar.setTrainer(basicsDTO.getTrainer());
        seminar.setAgenda(basicsDTO.getAgenda());
        seminar.setBookable(basicsDTO.isBookable());
        seminar.setCategory(basicsDTO.getCategory());
        seminar.setTargetLevel(basicsDTO.getTargetLevel());
        seminar.setRequirements(basicsDTO.getRequirements());
        seminar.setContactPerson(basicsDTO.getContactPerson());
        seminar.setTrainingType(basicsDTO.getTrainingType());
        seminar.setMaximumParticipants(basicsDTO.getMaximumParticipants());
        seminar.setCostsPerParticipant(basicsDTO.getCostsPerParticipant());
        seminar.setBookingTimelog(basicsDTO.getBookingTimelog());
        seminar.setGoal(basicsDTO.getGoal());
        seminar.setDuration(basicsDTO.getDuration());
        seminar.setCycle(basicsDTO.getCycle());
        seminar.setDates(basicsDTO.getDates());
    }
}
