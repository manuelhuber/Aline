package de.fh.rosenheim.repository;

import de.fh.rosenheim.domain.entity.User;
import de.fh.rosenheim.repository.UserRepository;
import de.fh.rosenheim.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

import static java.util.Arrays.asList;

/**
 * Configuration for persistence, like creating dummy data
 */
@Configuration
@EnableJpaRepositories("de.fh.rosenheim.repository")
public class PersistenceConfiguration extends JpaRepositoryConfigExtension {

    @Autowired
    private UserRepository userRepository;

    /**
     * Add dummy users
     */
    @PostConstruct
    private void addUsers() {
        User staff = User.builder()
                .username("user")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC")
                .authorities(Roles.USER)
                .build();
        User admin = User.builder()
                .username("admin")
                .password("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi")
                .authorities(Roles.DIVISION_HEAD)
                .build();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2050, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        User expired = User.builder()
                .username("expired")
                .password("$2a$10$PZ.A0IuNG958aHnKDzILyeD9k44EOi1Ny0VlAn.ygrGcgmVcg8PRK")
                .authorities(Roles.USER)
                .lastPasswordReset(date)
                .build();

        userRepository.save(asList(staff, admin, expired));
    }
}
