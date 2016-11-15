package de.fh.rosenheim.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookingKey implements Serializable {
    private User user;
    private Seminar seminar;
}
