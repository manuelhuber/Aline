package de.fh.rosenheim.repository;

import de.fh.rosenheim.domain.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByNameLike(String name);
}
