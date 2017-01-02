package de.fh.rosenheim.aline.model.dtos.json.view;

/**
 * Created by Manuel on 02.01.2017.
 */
public class View {

    public interface UserWithoutBooking {

    }


    public interface SeminarWithoutBooking {

    }


    public interface BookingOverview extends UserWithoutBooking, SeminarWithoutBooking {

    }
}
