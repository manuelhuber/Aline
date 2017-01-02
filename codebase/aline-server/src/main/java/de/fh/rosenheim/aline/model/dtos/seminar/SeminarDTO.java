package de.fh.rosenheim.aline.model.dtos.seminar;

import lombok.Data;

import java.util.Date;

@Data
public class SeminarDTO extends SeminarBasicsDTO {

    private long id;
    private int activeBookings;
    private Date created;
    private Date updated;
}
