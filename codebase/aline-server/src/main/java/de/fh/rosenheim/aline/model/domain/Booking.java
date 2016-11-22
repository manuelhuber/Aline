package de.fh.rosenheim.aline.model.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.fh.rosenheim.aline.model.base.DomainBase;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
public class Booking extends DomainBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne()
    @JoinColumn(name = "USERNAME", nullable = false)
    private User user;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne()
    @JoinColumn(name = "SEMINAR_ID", nullable = false)
    private Seminar seminar;

    private BookingStatus status;

    private Date creationDate;
}
