package de.fh.rosenheim.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fh.rosenheim.domain.base.DomainBase;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Builder()
// Needed for Hibernate
@NoArgsConstructor
// Needed for builder
@AllArgsConstructor
public class User extends DomainBase {

    private static final long serialVersionUID = 2353528370345499815L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @Getter(onMethod = @__(@JsonIgnore))
    private String password;
    private String division;
    @Getter(onMethod = @__(@JsonIgnore))
    private Date lastPasswordReset;
    @Getter(onMethod = @__(@JsonIgnore))
    private Date lastLogout;
    private String authorities;
}
