package de.fh.rosenheim.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
@Data
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
@IdClass(BookingKey.class)
public class Booking {

    @ManyToOne
    @JoinColumn(name = "seminar_id")
    @Id
    private Seminar seminar;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Id
    private User user;

    private BookingStatus status;

}
