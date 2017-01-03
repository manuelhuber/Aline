package de.fh.rosenheim.aline.model.dtos.json.view;

public class View {

    public interface UserBasicsView {

    }


    public interface SeminarBasicsView {

    }


    public interface SeminarView extends SeminarBasicsView {

    }


    public interface BookingSummaryView extends UserBasicsView, SeminarView {

    }


    public interface BillView extends UserBasicsView, SeminarBasicsView {

    }
}
