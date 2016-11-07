package de.fh.rosenheim.repository;

import de.fh.rosenheim.domain.entity.Training;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TrainingRepository extends CrudRepository<Training, Long> {

    List<Training> findByNameLike(String name);
}
