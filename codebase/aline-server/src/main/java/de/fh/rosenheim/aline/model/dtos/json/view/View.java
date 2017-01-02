package de.fh.rosenheim.aline.model.dtos.json.view;

public class View {

    public interface UserWithoutBookingView {

    }


    public interface SeminarBasicsView {

    }


    public interface SeminarView extends SeminarBasicsView {

    }


    public interface BookingSummaryView extends UserWithoutBookingView, SeminarView {

    }
}
