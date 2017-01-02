package de.fh.rosenheim.aline.model.domain;

import de.fh.rosenheim.aline.model.base.DomainBase;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@ToString(exclude = {"bookings"})
@EqualsAndHashCode(callSuper = true, of = {"username"})
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
public class User extends DomainBase {

    private static final long serialVersionUID = 2353528370345499815L;

    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String division;
    private Date lastPasswordReset;
    private Date lastLogout;

    /**
     * The authorities of the users in a comma-separated single string
     */
    private String authorities;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Booking> bookings = new HashSet<>();

    public void addBooking(Booking booking) {
        if (this.bookings == null) {
            this.bookings = new HashSet<>();
        }
        this.bookings.add(booking);
    }
}
