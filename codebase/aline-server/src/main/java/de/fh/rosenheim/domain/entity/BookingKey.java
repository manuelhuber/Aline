package de.fh.rosenheim.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
@Embeddable
public class BookingKey implements Serializable {

    @ManyToOne
    private User user;
    @ManyToOne
    private Seminar seminar;
}
