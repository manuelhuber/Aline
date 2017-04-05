package de.fh.rosenheim.aline.repository;

import de.fh.rosenheim.aline.model.domain.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findBySeminarId(long id);
}
