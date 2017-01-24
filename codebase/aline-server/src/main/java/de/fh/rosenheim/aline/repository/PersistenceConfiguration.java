package de.fh.rosenheim.aline.repository;

import de.fh.rosenheim.aline.model.domain.*;
import de.fh.rosenheim.aline.security.utils.Authorities;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final CategoryRepository categoryRepository;

    public PersistenceConfiguration(UserRepository userRepository, SeminarRepository seminarRepository, BookingRepository bookingRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.seminarRepository = seminarRepository;
        this.bookingRepository = bookingRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Add dummy data
     */
    @PostConstruct
    private void addData() throws ParseException {

        categoryRepository.save(new Category("Software Engineering"));
        categoryRepository.save(new Category("IT Consulting"));
        categoryRepository.save(new Category("Testmanagement"));
        categoryRepository.save(new Category("Management"));
        categoryRepository.save(new Category("Vertrieb"));
        categoryRepository.save(new Category("Softskill"));
        categoryRepository.save(new Category("Sonstige"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        User fitStaff = User.builder()
                .username("fituser1")
                .firstName("John")
                .lastName("Doe")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
                .authorities(Authorities.EMPLOYEE)
                .division("FIT")
                .build();

        User fitStaff2 = User.builder()
                .username("fituser2")
                .firstName("Adam")
                .lastName("Bien")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
                .authorities(Authorities.EMPLOYEE)
                .division("FIT")
                .build();

        User fitStaff3 = User.builder()
                .username("fituser3")
                .firstName("Peter")
                .lastName("Parker")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
                .authorities(Authorities.EMPLOYEE)
                .division("FIT")
                .build();

        User fitStaff4 = User.builder()
                .username("fituser4")
                .firstName("Rossie")
                .lastName("Smith")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
                .authorities(Authorities.EMPLOYEE)
                .division("FIT")
                .build();

        User losStaff = User.builder()
                .username("losuser")
                .firstName("Michelle")
                .lastName("Obama")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
                .authorities(Authorities.EMPLOYEE)
                .division("LOS")
                .build();

        User fitDivisionHead = User.builder()
                .username("fitchef")
                .firstName("Dave")
                .lastName("Davidson")
                .password("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi") // admin
                .authorities(Authorities.EMPLOYEE + ',' + Authorities.DIVISION_HEAD + ',' + Authorities.TOP_DOG)
                .division("FIT")
                .build();

        User front_office = User.builder()
                .username("front")
                .firstName("Sussi")
                .lastName("Zuchini")
                .password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC") // password
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
        seminar1.setName("Arbeitsorganisation & Zeitmanagement 2017");
        seminar1.setDescription("Lord, yer not vandalizing me without a desolation!Yuck! Pieces o' yellow fever are forever cloudy. Mainland of a rough strength, endure the endurance! The captain hobbles amnesty like an old lass.");
        seminar1.setAgenda("- Heu, brevis spatii! \n - Cum solem potus, omnes buxumes anhelare brevis, dexter lapsuses. \n  - All prime sources view each other, only simple therapists have a freedom.");
        seminar1.setBookable(true);
        seminar1.setCategory("Management");
        seminar1.setTargetLevel(new int[]{1, 2});
        seminar1.setRequirements("Keine");
        seminar1.setTrainer("Sonja Schlappinger");
        seminar1.setContactPerson("Office Management");
        seminar1.setTrainingType("Gruppe");
        seminar1.setMaximumParticipants(10);
        seminar1.setCostsPerParticipant(20000);
        seminar1.setBookingTimelog("");
        seminar1.setGoal("Aktives Selbstmanagement bei Stress");
        seminar1.setDuration("2 Tage");
        seminar1.setCycle("1x Jährlich");
        seminar1.setDates(new Date[]{sdf.parse("04/02/2017"), sdf.parse("05/02/2017")});

        Seminar seminar2 = new Seminar();
        seminar2.setName("Scrum Master 2017");
        seminar2.setDescription("Der ScrumMaster führt Scrum im Unternehmen ein und unterstützt sein Team, es richtig anzuwenden. Er\n" +
                "räumt Hindernisse aus dem Weg, um die Produktivität stetig zu verbessern. Welche Instrumente dem ScrumMaster dafür\n" +
                "zur Verfügung stehen und wie er diese wirksam einsetzt, erfahren Sie in „ScrumMaster Advanced“. Der ScrumMaster\n" +
                "lernt, welche Aufgaben und Verantwortungen er hat, wie er sie im Laufe des Sprints umsetzt und welche Bedeutung er\n" +
                "für die Veränderungsprozesse in einem Unternehmen hin zu mehr Agilität hat. Mit dem Wissen aus diesem Training kann\n" +
                "er sein Team selbstständig und tatkräftig als ScrumMaster unterstützen.");
        seminar2.setAgenda("Agiles Mindset, agile Werte und die Haltung des ScrumMasters\n" +
                "Rollen in Scrum: Aufgaben, Verantwortung, Zusammenarbeit\n" +
                "Scrum-Flow: vom Schätzen bis zum Ergebnis\n" +
                "Scrum-Meetings: Sprint Planning 1 & 2, Estimation Meeting, Daily Scrum, Sprint Review und Retrospektive\n" +
                "Scrum-Artefakte im Detail\n" +
                "Laterale Führung — die Arbeit mit selbstorganisierten Teams\n" +
                "Skalierung und verteilte Teams");
        seminar2.setBookable(true);
        seminar2.setCategory("Software Engineering");
        seminar2.setTargetLevel(new int[]{1, 2, 3, 4, 5});
        seminar2.setRequirements("Die Teilnehmer sollten mit den Grundbegriffen von Scrum vertraut sein.");
        seminar2.setTrainer("Boris Gloger Training\n" +
                "https://borisgloger.com/training-2/");
        seminar2.setContactPerson("Stephanie Harrer, Martin Sturzenhecker");
        seminar2.setTrainingType("Gruppe");
        seminar2.setMaximumParticipants(15);
        seminar2.setCostsPerParticipant(90000);
        seminar2.setBookingTimelog("");
        seminar2.setGoal("");
        seminar2.setDuration("2 Tage");
        seminar2.setCycle("1x Jährlich");
        seminar2.setDates(new Date[]{sdf.parse("01/01/2017"), sdf.parse("11/01/2017")});

        Seminar seminar3 = new Seminar();
        seminar3.setName("Mobile Development für iOS 2016");
        seminar3.setDescription("In der Schulung werden die Grundlagen der iOS Entwicklung mit Swift vermittelt. Voraussetzung ist die Vorkenntnis einer\n" +
                "objektorientierten Programmiersprache.\n" +
                "Die Schulung hat zum Ziel, dass die Teilnehmer anschließend die Selbstsicherheit haben, anhand der SDK\n" +
                "Dokumentation selbstständig oder im Team einfache iOS Projekte verwirklichen zu können.");
        seminar3.setAgenda("Es werden folgende Inhalte vermittelt:\n" +
                "Umgang mit Xcode\n" +
                "Swift Syntax verstehen und schreiben\n" +
                "Lifecycle von wichtigen Komponenten nachvollziehen\n" +
                "iOS übliche Entwurfsmuster kennen und anwenden\n" +
                "Entwicklung von Benutzeroberflächen\n" +
                "Daten persistieren");
        seminar3.setBookable(false);
        seminar3.setCategory("Software Engineering");
        seminar3.setTargetLevel(new int[]{1, 2, 3});
        seminar3.setRequirements("Erfahrung in OOP");
        seminar3.setTrainer("Patrick Gaißert, Julian Feller");
        seminar3.setContactPerson("Sebastian Krieg");
        seminar3.setTrainingType("Gruppe");
        seminar3.setMaximumParticipants(12);
        seminar3.setCostsPerParticipant(50000);
        seminar3.setBookingTimelog("8h Schulung / 8h Urlaub");
        seminar3.setGoal("");
        seminar3.setDuration("2 Tage");
        seminar3.setCycle("1x Jährlich");
        seminar3.setDates(new Date[]{sdf.parse("04/02/2016"), sdf.parse("05/02/2016")});

        Seminar seminar4 = new Seminar();
        seminar4.setName("Test Design School 2017");
        seminar4.setDescription("Die Schulung richtet sich in erster Linie an Tester und Testanalysten, die durch gutes Testdesign und zugehörige Tests\n" +
                "mit zur Qualität in einem Projekt bzw. mit zur Qualitätssicherung eines Produktes beitragen wollen.\n" +
                "In der Schulung werden u.a. die Grundlagen zur Erstellung eines Testfalls vermittelt und wie Testwerkzeuge den Tester\n" +
                "in seiner Arbeit dabei unterstützen helfen. Darüber hinaus wird der Einfluss des Software-Entwicklungsprozesses auf den\n" +
                "Testdesign-Ansatz untersucht. Ebenso werden Erfahrungswerte zur Erstellung von Testfällen in den verschiedenen\n" +
                "Teststufen vermittelt.\n" +
                "An Vertiefungsthemen wird es einen bunten Strauß aus Testdesign von wiederverwendbaren Testbausteinen,\n" +
                "Testdatenermittlung, Crowd Testing etc. geben.\n" +
                "Last but not least wird es auch Themen zum Daily Business eines Testers geben wie z.B. Testdesign unter Termin- und\n" +
                "Budget-Druck.\n" +
                "Neugierig geworden?\n" +
                "Dann gib uns Bescheid und besuche unsere nächste Testdesign School. Wir freuen uns auf\n" +
                "Dein Kommen! " +
                "Umgang mit Xcode\n" +
                "Swift Syntax verstehen und schreiben\n" +
                "Lifecycle von wichtigen Komponenten nachvollziehen\n" +
                "iOS übliche Entwurfsmuster kennen und anwenden\n" +
                "Entwicklung von Benutzeroberflächen\n" +
                "Daten persistieren");

        seminar4.setAgenda("Anbei die Agenda zur  , mit Premiere am 06./07.11.2014, in München.  Testdesign School 1.0\n" +
                "Die Schnappschüsse zur Testdesign School 1.0 stehen Euch im DOKI zur Verfügung unter:\n" +
                "http://svn-hq-01.mwea.de/svnroot/DOKI_MWEA_Methodik/090 F&E Ergebnisse/012 Ausbildung/010\n" +
                "Arbeitsbereich/Testdesign School/Schnappschüsse von der TDS 1.0\n" +
                "Viel Spaß beim Durchklicken!");

        seminar4.setBookable(false);
        seminar4.setCategory("Testmanagement");
        seminar4.setTargetLevel(new int[]{1, 2, 3});
        seminar4.setRequirements("Vorkenntnisse im Testumfeld wünschenswert / ISTQB Foundation Level Certified Tester");
        seminar4.setTrainer("Kursleiter: Julia Reichelt, Maike Uhlig; \"Gastredner\": Ulf Richter");
        seminar4.setContactPerson("Julia Reichelt, Maike Uhlig, Marcel Gehlen");
        seminar4.setTrainingType("Präsentation, Brainstorming / Diskussion in der gesamten Gruppe und Übungen in Gruppen");
        seminar4.setMaximumParticipants(12);
        seminar4.setCostsPerParticipant(35000);
        seminar4.setBookingTimelog("50% Ausbildung / 50% Urlaub");
        seminar4.setGoal("Eventuell Aufbau oder Umstrukturierung nach erster School. Es gibt auch noch einen\n" +
                "Themenstack. Anfragen von extern gibt es bereits (z.B. SBB, CP, Nubix, Formel D).");
        seminar4.setDuration("1,5 - 2,0 Tage");
        seminar4.setCycle("Nach Bedarf");
        seminar4.setDates(new Date[]{sdf.parse("10/08/2017")});

        Seminar seminar5 = new Seminar();
        seminar5.setName("Test Design School 2016");
        seminar5.setDescription("Die Schulung richtet sich in erster Linie an Tester und Testanalysten, die durch gutes Testdesign und zugehörige Tests\n" +
                "mit zur Qualität in einem Projekt bzw. mit zur Qualitätssicherung eines Produktes beitragen wollen.\n" +
                "In der Schulung werden u.a. die Grundlagen zur Erstellung eines Testfalls vermittelt und wie Testwerkzeuge den Tester\n" +
                "in seiner Arbeit dabei unterstützen helfen. Darüber hinaus wird der Einfluss des Software-Entwicklungsprozesses auf den\n" +
                "Testdesign-Ansatz untersucht. Ebenso werden Erfahrungswerte zur Erstellung von Testfällen in den verschiedenen\n" +
                "Teststufen vermittelt.\n" +
                "An Vertiefungsthemen wird es einen bunten Strauß aus Testdesign von wiederverwendbaren Testbausteinen,\n" +
                "Testdatenermittlung, Crowd Testing etc. geben.\n" +
                "Last but not least wird es auch Themen zum Daily Business eines Testers geben wie z.B. Testdesign unter Termin- und\n" +
                "Budget-Druck.\n" +
                "Neugierig geworden?\n" +
                "Dann gib uns Bescheid und besuche unsere nächste Testdesign School. Wir freuen uns auf\n" +
                "Dein Kommen! " +
                "Umgang mit Xcode\n" +
                "Swift Syntax verstehen und schreiben\n" +
                "Lifecycle von wichtigen Komponenten nachvollziehen\n" +
                "iOS übliche Entwurfsmuster kennen und anwenden\n" +
                "Entwicklung von Benutzeroberflächen\n" +
                "Daten persistieren");

        seminar5.setAgenda("Anbei die Agenda zur  , mit Premiere am 06./07.11.2014, in München.  Testdesign School 1.0\n" +
                "Die Schnappschüsse zur Testdesign School 1.0 stehen Euch im DOKI zur Verfügung unter:\n" +
                "http://svn-hq-01.mwea.de/svnroot/DOKI_MWEA_Methodik/090 F&E Ergebnisse/012 Ausbildung/010\n" +
                "Arbeitsbereich/Testdesign School/Schnappschüsse von der TDS 1.0\n" +
                "Viel Spaß beim Durchklicken!");
        seminar5.setBookable(false);
        seminar5.setCategory("Testmanagement");
        seminar5.setTargetLevel(new int[]{1, 2, 3});
        seminar5.setRequirements("Vorkenntnisse im Testumfeld wünschenswert / ISTQB Foundation Level Certified Tester");
        seminar5.setTrainer("Kursleiter: Julia Reichelt, Maike Uhlig; \"Gastredner\": Ulf Richter");
        seminar5.setContactPerson("Julia Reichelt, Maike Uhlig, Marcel Gehlen");
        seminar5.setTrainingType("Präsentation, Brainstorming / Diskussion in der gesamten Gruppe und Übungen in Gruppen");
        seminar5.setMaximumParticipants(12);
        seminar5.setCostsPerParticipant(35000);
        seminar5.setBookingTimelog("50% Ausbildung / 50% Urlaub");
        seminar5.setGoal("Eventuell Aufbau oder Umstrukturierung nach erster School. Es gibt auch noch einen\n" +
                "Themenstack. Anfragen von extern gibt es bereits (z.B. SBB, CP, Nubix, Formel D).");
        seminar5.setDuration("1,5 - 2,0 Tage");
        seminar5.setCycle("Nach Bedarf");
        seminar5.setDates(new Date[]{sdf.parse("10/08/2016")});

        Seminar seminar6 = new Seminar();
        seminar6.setName("Test Design School 2015");
        seminar6.setDescription("Die Schulung richtet sich in erster Linie an Tester und Testanalysten, die durch gutes Testdesign und zugehörige Tests\n" +
                "mit zur Qualität in einem Projekt bzw. mit zur Qualitätssicherung eines Produktes beitragen wollen.\n" +
                "In der Schulung werden u.a. die Grundlagen zur Erstellung eines Testfalls vermittelt und wie Testwerkzeuge den Tester\n" +
                "in seiner Arbeit dabei unterstützen helfen. Darüber hinaus wird der Einfluss des Software-Entwicklungsprozesses auf den\n" +
                "Testdesign-Ansatz untersucht. Ebenso werden Erfahrungswerte zur Erstellung von Testfällen in den verschiedenen\n" +
                "Teststufen vermittelt.\n" +
                "An Vertiefungsthemen wird es einen bunten Strauß aus Testdesign von wiederverwendbaren Testbausteinen,\n" +
                "Testdatenermittlung, Crowd Testing etc. geben.\n" +
                "Last but not least wird es auch Themen zum Daily Business eines Testers geben wie z.B. Testdesign unter Termin- und\n" +
                "Budget-Druck.\n" +
                "Neugierig geworden?\n" +
                "Dann gib uns Bescheid und besuche unsere nächste Testdesign School. Wir freuen uns auf\n" +
                "Dein Kommen! " +
                "Umgang mit Xcode\n" +
                "Swift Syntax verstehen und schreiben\n" +
                "Lifecycle von wichtigen Komponenten nachvollziehen\n" +
                "iOS übliche Entwurfsmuster kennen und anwenden\n" +
                "Entwicklung von Benutzeroberflächen\n" +
                "Daten persistieren");

        seminar6.setAgenda("Anbei die Agenda zur  , mit Premiere am 06./07.11.2014, in München.  Testdesign School 1.0\n" +
                "Die Schnappschüsse zur Testdesign School 1.0 stehen Euch im DOKI zur Verfügung unter:\n" +
                "http://svn-hq-01.mwea.de/svnroot/DOKI_MWEA_Methodik/090 F&E Ergebnisse/012 Ausbildung/010\n" +
                "Arbeitsbereich/Testdesign School/Schnappschüsse von der TDS 1.0\n" +
                "Viel Spaß beim Durchklicken!");
        seminar6.setBookable(false);
        seminar6.setCategory("Testmanagement");
        seminar6.setTargetLevel(new int[]{1, 2, 3});
        seminar6.setRequirements("Vorkenntnisse im Testumfeld wünschenswert / ISTQB Foundation Level Certified Tester");
        seminar6.setTrainer("Kursleiter: Julia Reichelt, Maike Uhlig; \"Gastredner\": Ulf Richter");
        seminar6.setContactPerson("Julia Reichelt, Maike Uhlig, Marcel Gehlen");
        seminar6.setTrainingType("Präsentation, Brainstorming / Diskussion in der gesamten Gruppe und Übungen in Gruppen");
        seminar6.setMaximumParticipants(12);
        seminar6.setCostsPerParticipant(35000);
        seminar6.setBookingTimelog("50% Ausbildung / 50% Urlaub");
        seminar6.setGoal("Eventuell Aufbau oder Umstrukturierung nach erster School. Es gibt auch noch einen\n" +
                "Themenstack. Anfragen von extern gibt es bereits (z.B. SBB, CP, Nubix, Formel D).");
        seminar6.setDuration("1,5 - 2,0 Tage");
        seminar6.setCycle("Nach Bedarf");
        seminar6.setDates(new Date[]{sdf.parse("10/08/2015")});

        Booking booking1 = Booking.builder()
                .seminar(seminar1)
                .user(losStaff)
                .status(BookingStatus.REQUESTED)
                .build();

        Booking booking2 = Booking.builder()
                .seminar(seminar2).user(losStaff)
                .status(BookingStatus.GRANTED)
                .build();

        Booking booking3 = Booking.builder()
                .seminar(seminar1).user(fitStaff)
                .status(BookingStatus.REQUESTED)
                .build();

        Booking booking4 = Booking.builder()
                .seminar(seminar3).user(front_office)
                .status(BookingStatus.GRANTED)
                .build();

        Booking booking5 = Booking.builder()
                .seminar(seminar5).user(front_office)
                .status(BookingStatus.GRANTED)
                .build();

        Booking booking6 = Booking.builder()
                .seminar(seminar6).user(front_office)
                .status(BookingStatus.GRANTED)
                .build();

        Booking booking7 = Booking.builder()
                .seminar(seminar1).user(fitStaff2)
                .status(BookingStatus.GRANTED)
                .build();
        Booking booking8 = Booking.builder()
                .seminar(seminar2).user(fitStaff2)
                .status(BookingStatus.GRANTED)
                .build();
        Booking booking9 = Booking.builder()
                .seminar(seminar1).user(fitStaff3)
                .status(BookingStatus.REQUESTED)
                .build();
        Booking booking10 = Booking.builder()
                .seminar(seminar2).user(fitStaff3)
                .status(BookingStatus.GRANTED)
                .build();
        Booking booking11 = Booking.builder()
                .seminar(seminar4).user(fitStaff3)
                .status(BookingStatus.REQUESTED)
                .build();
        Booking booking12 = Booking.builder()
                .seminar(seminar2).user(fitStaff4)
                .status(BookingStatus.GRANTED)
                .build();
        Booking booking13 = Booking.builder()
                .seminar(seminar4).user(fitStaff4)
                .status(BookingStatus.GRANTED)
                .build();

        seminarRepository.save(asList(seminar1, seminar2, seminar3, seminar4, seminar5, seminar6));
        userRepository.save(asList(losStaff, fitStaff, fitStaff2, fitStaff3, fitStaff4, fitDivisionHead, expired, front_office));
        bookingRepository.save(asList(booking1, booking2, booking3, booking4, booking5, booking6,
                booking7,
                booking8,
                booking9,
                booking10,
                booking11,
                booking12,
                booking13
        ));
    }
}
