package de.fh.rosenheim.aline.model.dtos.seminar;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SeminarBasicsDTO {

    private String name;

    private String description;

    private String agenda;

    private boolean bookable;

    @ApiModelProperty(required = true)
    private String category;

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
}
