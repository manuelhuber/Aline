package de.fh.rosenheim.aline.model.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fh.rosenheim.aline.model.base.DomainBase;
import io.swagger.annotations.ApiModelProperty;
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

    // Swagger doesn't recognize JsonIdentityReference, so we have to set the type manually
    @ApiModelProperty(dataType = "java.lang.String")
    // When serializing, only use reference
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "username", required = true)
    @ManyToOne()
    @JoinColumn(name = "USERNAME", nullable = false)
    private User user;

    // Swagger doesn't recognize JsonIdentityReference, so we have to set the type manually
    @ApiModelProperty(dataType = "java.lang.Long")
    // When serializing, only use reference
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "seminarId", required = true)
    @ManyToOne()
    @JoinColumn(name = "SEMINAR_ID", nullable = false)
    private Seminar seminar;

    private BookingStatus status;

    private Date creationDate;
}
