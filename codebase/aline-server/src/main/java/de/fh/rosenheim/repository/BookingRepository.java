package de.fh.rosenheim.repository;

import de.fh.rosenheim.domain.entity.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface BookingRepository extends CrudRepository<Booking, Long> {

}
