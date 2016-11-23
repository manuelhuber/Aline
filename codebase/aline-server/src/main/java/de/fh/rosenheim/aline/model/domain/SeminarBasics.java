package de.fh.rosenheim.aline.model.domain;

import de.fh.rosenheim.aline.model.base.DomainBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
/**
 * The basics of a seminar that can easily be changed
 */
public class SeminarBasics extends DomainBase {

    private String name;
    @Column( length = 100000 )
    private String description;
    @Column( length = 100000 )
    private String agenda;
    private boolean bookable;
    // The employees are grouped in 5 levels, depending on skill in their field
    private int[] targetLevel;
    private String requirements;
    private String trainer;
    private String contactPerson;
    private String trainingType;
    private int maximumParticipants;
    private int costsPerParticipant;
    private String bookingTimelog;
    private String goal;
    private String duration;
    private String cycle;
    private Date[] dates;

    private String reporting;

    /**
     * Easy way to copy basic data
     */
    public void copyBasics(SeminarBasics newData) {
        name = newData.getName();
        description = newData.getDescription();
        trainer = newData.getTrainer();
        agenda = newData.getAgenda();
        bookable = newData.isBookable();
        targetLevel = newData.getTargetLevel();
        requirements = newData.getRequirements();
        reporting = newData.getReporting();
        contactPerson = newData.getContactPerson();
        trainingType = newData.getTrainingType();
        maximumParticipants = newData.getMaximumParticipants();
        costsPerParticipant = newData.getCostsPerParticipant();
        bookingTimelog = newData.getBookingTimelog();
        goal = newData.getGoal();
        duration = newData.getDuration();
        cycle = newData.getCycle();
        dates = newData.getDates();
    }
}
