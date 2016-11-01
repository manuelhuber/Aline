package de.fh.rosenheim.domain.entity;

import de.fh.rosenheim.domain.base.DomainBase;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Builder()
public class User extends DomainBase {

    private static final long serialVersionUID = 2353528370345499815L;

//    public User() {
//        super();
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date lastPasswordReset;
    private String authorities;
}
