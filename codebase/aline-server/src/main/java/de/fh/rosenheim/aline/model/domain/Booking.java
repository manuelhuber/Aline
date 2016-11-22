package de.fh.rosenheim.aline.model.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("username")
    @ManyToOne()
    @JoinColumn(name = "USERNAME", nullable = false)
    private User user;

    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("seminarId")
    @ManyToOne()
    @JoinColumn(name = "SEMINAR_ID", nullable = false)
    private Seminar seminar;

    private BookingStatus status;

    private Date creationDate;
}
