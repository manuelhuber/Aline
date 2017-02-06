package de.fh.rosenheim.aline.model.dtos.json.view;

public class View {

    public interface UserBasicsView {

    }


    public interface SeminarBasicsView {

    }


    public interface SeminarIdView {

    }


    public interface SeminarDetailsView extends SeminarBasicsView {

    }


    public interface BookingSummaryView extends UserBasicsView, SeminarIdView {

    }


    public interface BookingSummaryWithSeminarDetailsView extends BookingSummaryView, SeminarDetailsView {

    }


    public interface BillView extends UserBasicsView, SeminarBasicsView {

    }
}
