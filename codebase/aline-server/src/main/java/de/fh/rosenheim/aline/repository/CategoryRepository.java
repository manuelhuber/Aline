package de.fh.rosenheim.aline.repository;

import de.fh.rosenheim.aline.model.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CategoryRepository extends CrudRepository<Category, String> {

}
