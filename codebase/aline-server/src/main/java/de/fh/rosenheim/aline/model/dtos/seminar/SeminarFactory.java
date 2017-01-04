package de.fh.rosenheim.aline.model.dtos.seminar;

import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.util.SeminarUtil;

/**
 * Generates and transforms all Seminar models & DTOs
 */
public class SeminarFactory {

    /**
     * Generates a SeminarDTO for the given seminar
     *
     * @return SeminarDTO
     */
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
        dto.setBillGenerated(seminar.isBillGenerated());
        dto.setActiveBookings(SeminarUtil.getActiveBookingCount(seminar));
        dto.setCreated(seminar.getCreated());
        dto.setUpdated(seminar.getUpdated());
        dto.setId(seminar.getId());
        return dto;
    }

    /**
     * Generates a new seminar from the BasicsDTO
     *
     * @return Seminar
     */
    public static Seminar createSeminar(SeminarBasicsDTO basicsDTO) {
        Seminar seminar = new Seminar();
        updateSeminar(seminar, basicsDTO);
        return seminar;
    }

    /**
     * Copies the basic data from the DTO to the given seminar
     *
     * @return The updated Seminar
     */
    public static Seminar updateSeminar(Seminar seminar, SeminarBasicsDTO basicsDTO) {
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
        return seminar;
    }
}
