package de.fh.rosenheim.domain.entity;

import de.fh.rosenheim.domain.base.DomainBase;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trainings")
@Data
public class Seminar extends DomainBase {

    private static final long serialVersionUID = 2353528359632158741L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String trainer;
    private String agenda;
    private boolean bookable;
    private String targetAudiance;
    private String requirements;
    private String reporting;
    private String contactPerson;
    private String trainingType;
    private int maximumParticipants;
    private int costsPerParticipant;
    private String bookingTimelog;
    private String planedAdvancement;
    private int duration;
    private String regularCycle;
    private String dates;
    private Date creationDate;
}
