package de.fh.rosenheim.domain.entity;

import de.fh.rosenheim.domain.base.DomainBase;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
public class Booking extends DomainBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEMINARID", nullable = false)
    private Seminar seminar;

    private BookingStatus status;
}
