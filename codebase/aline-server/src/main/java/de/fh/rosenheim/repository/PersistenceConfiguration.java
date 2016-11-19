package de.fh.rosenheim.repository;

import de.fh.rosenheim.domain.entity.Booking;
import de.fh.rosenheim.domain.entity.BookingStatus;
import de.fh.rosenheim.domain.entity.Seminar;
import de.fh.rosenheim.domain.entity.User;
import de.fh.rosenheim.security.utils.Authorities;
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

    @Autowired
    private SeminarRepository seminarRepository;

    @Autowired
    private BookingRepository bookingRepository;

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

        Seminar seminar1 = Seminar.builder()
                .name("Programmieren 101")
                .description("Alles was man wissen muss")
                .trainer("Joe")
                .build();

        Seminar seminar2 = Seminar.builder()
                .name("Kundengespräche führen")
                .description("Wenig reden, viel sagen")
                .trainer("Peter")
                .build();


        Booking booking1 = Booking.builder()
                .seminar(seminar1)
                .user(losStaff)
                .status(BookingStatus.REQUESTED)
                .build();

        Booking booking2 = Booking.builder()
                .seminar(seminar2).user(losStaff)
                .status(BookingStatus.REQUESTED)
                .build();

        Booking booking3 = Booking.builder()
                .seminar(seminar1).user(fitStaff)
                .status(BookingStatus.REQUESTED)
                .build();

        seminarRepository.save(asList(seminar1, seminar2));
        userRepository.save(asList(losStaff, fitStaff, fitDivisonHead, expired, front_office));
        bookingRepository.save(asList(booking1, booking2, booking3));
    }
}
