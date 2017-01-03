package de.fh.rosenheim.aline.unit.util;

import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.util.SeminarUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SeminarUtilTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void getLastDate() throws ParseException {
        Seminar seminar = new Seminar();
        seminar.setDates((Date[])
                Arrays.asList(
                        sdf.parse("12/1/2015"),
                        sdf.parse("12/1/2018"),
                        sdf.parse("12/1/2017")
                ).toArray());

        assertThat(SeminarUtil.getLastDate(seminar)).isEqualTo(sdf.parse("12/1/2018"));
    }

    @Test
    public void getLastDateFromNull() throws ParseException {
        Seminar seminar = new Seminar();
        assertThat(SeminarUtil.getLastDate(seminar)).isEqualTo(null);
    }

    @Test
    public void getLastDateFromEmpty() throws ParseException {
        Seminar seminar = new Seminar();
        seminar.setDates(new Date[0]);
        assertThat(SeminarUtil.getLastDate(seminar)).isEqualTo(null);
    }

    @Test
    public void getYearForSeminar() throws ParseException {
        Seminar seminar = new Seminar();
        seminar.setDates((Date[])
                Arrays.asList(
                        sdf.parse("12/1/2015"),
                        sdf.parse("12/1/2018"),
                        sdf.parse("12/1/2017")
                ).toArray());

        assertThat(SeminarUtil.getYear(seminar)).isEqualTo(2018);
    }

    @Test
    public void getYearFromNull() throws ParseException {
        Seminar seminar = new Seminar();
        assertThat(SeminarUtil.getYear(seminar)).isEqualTo(
                Calendar.getInstance().get(Calendar.YEAR)
        );
    }

    @Test
    public void getYearFromEmpty() throws ParseException {
        Seminar seminar = new Seminar();
        seminar.setDates(new Date[0]);
        assertThat(SeminarUtil.getYear(seminar)).isEqualTo(
                Calendar.getInstance().get(Calendar.YEAR)
        );
    }
}
