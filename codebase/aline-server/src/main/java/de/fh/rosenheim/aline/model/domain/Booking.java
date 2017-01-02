package de.fh.rosenheim.aline.model.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fh.rosenheim.aline.model.base.DomainBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Getter
@EqualsAndHashCode(callSuper = true, of = {"id"})
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
public class Booking extends DomainBase {

    /**
     * Actually a Booking should be identified by the combination of user & seminar but
     * I couldn't get a composite primary key working exactly the way I wanted in a reasonable amount of time.
     * It should only save the reference via ID in the database, have the full object in Code, only use ID when
     * serializing to JSON and automatically be deleted if either seminar or user is deleted.
     * So I'm going the not so pretty route of giving it a separate ID and checking (when necessary) if the
     * seminar / user booking already exists manually.
     */
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Swagger doesn't recognize JsonIdentityReference, so we have to set the type manually
    @ApiModelProperty(required = true, dataType = "java.lang.String")
    // When serializing, only use reference
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "username", required = true)
    @ManyToOne()
    @JoinColumn(name = "USERNAME", nullable = false)
    @Setter
    private User user;

    // Swagger doesn't recognize JsonIdentityReference, so we have to set the type manually
    @ApiModelProperty(required = true, dataType = "java.lang.Long")
    // When serializing, only use reference
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "seminarId", required = true)
    @ManyToOne()
    @JoinColumn(name = "SEMINAR_ID", nullable = false)
    @Setter
    private Seminar seminar;

    @Setter
    private BookingStatus status;

    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;
}
