package de.fh.rosenheim.configuration;

import de.fh.rosenheim.model.Employee;
import de.fh.rosenheim.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;

/**
 * Created by Manuel on 21.10.2016.
 */
@Configuration
@EnableJpaRepositories("de.fh.rosenheim.repository")
public class PersistenceConfiguration extends JpaRepositoryConfigExtension {

    @Autowired
    private EmployeeRepository employeeRepository;


    @PostConstruct
    private void addPersons() {
        Employee john = new Employee();
        john.setName("John");
        Employee mary = new Employee();
        mary.setName("Mary");
        employeeRepository.save(asList(john, mary));
    }
}
