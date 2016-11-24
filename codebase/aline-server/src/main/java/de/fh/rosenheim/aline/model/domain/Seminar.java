package de.fh.rosenheim.aline.model.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@EqualsAndHashCode(callSuper = true, of = {"id"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Seminar extends SeminarBasics {

    private static final long serialVersionUID = 2353528359632158741L;
    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private Long id;

    @OneToMany(mappedBy = "seminar", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
    private Set<Booking> bookings = new HashSet<>();

    public Seminar() {
        super();
    }

    public Seminar(SeminarBasics basics) {
        super();
        copyBasics(basics);
    }

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
