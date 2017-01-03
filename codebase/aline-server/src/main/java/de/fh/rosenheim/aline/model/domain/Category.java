package de.fh.rosenheim.aline.model.domain;

import de.fh.rosenheim.aline.model.base.DomainBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A simple way to store the categories in the database.
 * We don't want to use an enum since they need to be added
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends DomainBase {

    @Id
    private String name;

    public String getName() {
        return name;
    }
}
