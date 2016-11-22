package de.fh.rosenheim.aline.repository;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.security.utils.Authorities;
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
@EnableJpaRepositories("de.fh.rosenheim.aline.repository")
public class PersistenceConfiguration extends JpaRepositoryConfigExtension {

    private final UserRepository userRepository;

    private final SeminarRepository seminarRepository;

    private final BookingRepository bookingRepository;

    public PersistenceConfiguration(UserRepository userRepository, BookingRepository bookingRepository, SeminarRepository seminarRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.seminarRepository = seminarRepository;
    }

    /**
     * Add dummy data
     */
    @PostConstruct
    private void addData() {
        User fitStaff = User.builder()
                .username("fituser")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
                .authorities(Authorities.EMPLOYEE)
                .division("FIT")
                .build();

        User losStaff = User.builder()
                .username("losuser")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
                .authorities(Authorities.EMPLOYEE)
                .division("LOS")
                .build();

        User fitDivisonHead = User.builder()
                .username("admin")
                .password("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi") // admin
                .authorities(Authorities.EMPLOYEE + ',' + Authorities.DIVISION_HEAD)
                .division("FIT")
                .build();

        User front_office = User.builder()
                .username("front")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // admin
                .authorities(Authorities.EMPLOYEE + ',' + Authorities.FRONT_OFFICE)
                .division("FOO")
                .build();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2050, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        User expired = User.builder()
                .username("expired")
                .password("$2a$10$PZ.A0IuNG958aHnKDzILyeD9k44EOi1Ny0VlAn.ygrGcgmVcg8PRK")
                .authorities(Authorities.EMPLOYEE)
                .lastPasswordReset(date)
                .build();

        Seminar seminar1 = new Seminar();
        seminar1.setName("Programmieren 101");
        seminar1.setDescription("Alles was man wissen muss");
        seminar1.setTrainer("Joe");

        Seminar seminar2 = new Seminar();
        seminar2.setName("Kundengespräche führen");
        seminar2.setDescription("Wenig reden, viel sagen");
        seminar2.setTrainer("Peter");

        Booking booking1 = Booking.builder()
                .seminar(seminar1)
                .user(losStaff)
                .status(BookingStatus.REQUESTED)
                .creationDate(new Date())
                .build();

        Booking booking2 = Booking.builder()
                .seminar(seminar2).user(losStaff)
                .status(BookingStatus.REQUESTED)
                .creationDate(new Date())
                .build();

        Booking booking3 = Booking.builder()
                .seminar(seminar1).user(fitStaff)
                .status(BookingStatus.REQUESTED)
                .creationDate(new Date())
                .build();

        seminarRepository.save(asList(seminar1, seminar2));
        userRepository.save(asList(losStaff, fitStaff, fitDivisonHead, expired, front_office));
        bookingRepository.save(asList(booking1, booking2, booking3));
    }
}
