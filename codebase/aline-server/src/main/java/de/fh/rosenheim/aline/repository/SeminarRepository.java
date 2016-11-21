package de.fh.rosenheim.aline.repository;

import de.fh.rosenheim.aline.domain.entity.Seminar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface SeminarRepository extends CrudRepository<Seminar, Long> {

    List<Seminar> findByNameLike(String name);
}
