package de.fh.rosenheim.aline.model.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seminars")
@Getter
@Setter
@ToString(exclude = {"bookings"})
@EqualsAndHashCode(of = {"id"})
public class Seminar {

    private static final long serialVersionUID = 2353528359632158741L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "seminar", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Booking> bookings = new HashSet<>();

    private String name;

    @Column(length = 100000)
    private String description;

    @Column(length = 100000)
    private String agenda;

    private boolean bookable;

    // This is just a string and not a Category object for simplicity sake, i.e. while (de-)serializing.
    // We check the validity of the string in the SeminarService when creating & updating a seminar
    private String category;

    // The employees are grouped in 5 levels, depending on skill in their field
    private int[] targetLevel;

    private String requirements;

    private String trainer;

    private String contactPerson;

    private String trainingType;

    private int maximumParticipants;

    /**
     * In euro cent
     * 1234 = 12,23 Euro
     */
    private long costsPerParticipant;

    /**
     * A description on how employees can book the time as working hours
     */
    private String bookingTimelog;

    private String goal;

    private String duration;

    private String cycle;

    private Date[] dates;

    private boolean billGenerated;

    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;
}
