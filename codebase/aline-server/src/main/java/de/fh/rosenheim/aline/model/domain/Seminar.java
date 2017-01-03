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
@ToString(exclude = {"bookings"})
@EqualsAndHashCode(of = {"id"})
public class Seminar {

    private static final long serialVersionUID = 2353528359632158741L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private Long id;

    @OneToMany(mappedBy = "seminar", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Booking> bookings = new HashSet<>();

    @Setter
    private String name;

    @Column(length = 100000)
    @Setter
    private String description;

    @Column(length = 100000)
    @Setter
    private String agenda;

    @Setter
    private boolean bookable;

    // This is just a string and not a Category object for simplicity sake, i.e. while (de-)serializing.
    // We check the validity of the string in the SeminarService when creating & updating a seminar
    @Setter
    private String category;

    // The employees are grouped in 5 levels, depending on skill in their field
    @Setter
    private int[] targetLevel;

    @Setter
    private String requirements;

    @Setter
    private String trainer;

    @Setter
    private String contactPerson;

    @Setter
    private String trainingType;

    @Setter
    private int maximumParticipants;

    /**
     * In euro cent
     * 1234 = 12,23 Euro
     */
    @Setter
    private long costsPerParticipant;

    /**
     * A description on how employees can book the time as working hours
     */
    @Setter
    private String bookingTimelog;

    @Setter
    private String goal;

    @Setter
    private String duration;

    @Setter
    private String cycle;

    @Setter
    private Date[] dates;

    @Setter
    private boolean billGenerated;

    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;
}
