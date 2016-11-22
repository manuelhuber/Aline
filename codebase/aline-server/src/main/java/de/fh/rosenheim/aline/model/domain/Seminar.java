package de.fh.rosenheim.aline.model.domain;

import de.fh.rosenheim.aline.model.base.DomainBase;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seminars")
@Getter
@Setter
@ToString(exclude = {"bookings"})
@EqualsAndHashCode(callSuper = true, exclude = {"bookings"})
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
public class Seminar extends DomainBase {

    private static final long serialVersionUID = 2353528359632158741L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String trainer;
    private String agenda;
    private boolean bookable;
    private String targetAudiance;
    private String requirements;
    private String reporting;
    private String contactPerson;
    private String trainingType;
    private int maximumParticipants;
    private int costsPerParticipant;
    private String bookingTimelog;
    private String planedAdvancement;
    private int duration;
    private String regularCycle;
    private String dates;
    private Date creationDate;

    @OneToMany(mappedBy = "seminar", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Booking> bookings = new HashSet<>();

    public void addBooking(Booking booking) {
        if (this.bookings == null) {
            this.bookings = new HashSet<>();
        }
        this.bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }
}
