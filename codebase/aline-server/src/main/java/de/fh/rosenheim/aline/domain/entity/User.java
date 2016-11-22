package de.fh.rosenheim.aline.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fh.rosenheim.aline.domain.base.DomainBase;
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
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"bookings"})
@EqualsAndHashCode(callSuper = true, exclude = {"bookings"})
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
public class User extends DomainBase {

    private static final long serialVersionUID = 2353528370345499815L;

    @Id
    private String username;
    @Getter(onMethod = @__(@JsonIgnore))
    private String password;
    private String division;
    @Getter(onMethod = @__(@JsonIgnore))
    private Date lastPasswordReset;
    @Getter(onMethod = @__(@JsonIgnore))
    private Date lastLogout;
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
