package de.fh.rosenheim.repository;

import de.fh.rosenheim.model.Employee;
import de.fh.rosenheim.security.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    @PreAuthorize("hasRole('" + Roles.DIVISION_HEAD + "')")
    Page<Employee> findAll(Pageable pageable);

    @Override
    @PostAuthorize("returnObject.name == principal.username or hasRole('" + Roles.DIVISION_HEAD + "')")
    Employee findOne(Long aLong);

    @PreAuthorize("hasRole('" + Roles.DIVISION_HEAD + "')")
    List<Employee> findByNameLike(@Param("name") String name);

    @RestResource(exported = false)
    Employee findByNameEquals(String firstName);
}
