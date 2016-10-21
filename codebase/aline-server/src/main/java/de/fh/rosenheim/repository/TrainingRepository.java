package de.fh.rosenheim.repository;

import de.fh.rosenheim.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByNameLike(@Param("name") String name);
}
