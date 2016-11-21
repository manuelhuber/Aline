package de.fh.rosenheim.aline.domain.entity;

import de.fh.rosenheim.aline.domain.base.DomainBase;
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
    @JoinColumn(name = "USERNAME", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEMINAR_ID", nullable = false)
    private Seminar seminar;

    private BookingStatus status;
}
