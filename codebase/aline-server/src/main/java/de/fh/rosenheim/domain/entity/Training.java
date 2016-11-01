package de.fh.rosenheim.domain.entity;

import de.fh.rosenheim.domain.base.DomainBase;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "trainings")
@Data
public class Training extends DomainBase {

    private static final long serialVersionUID = 2353528359632158741L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
}
