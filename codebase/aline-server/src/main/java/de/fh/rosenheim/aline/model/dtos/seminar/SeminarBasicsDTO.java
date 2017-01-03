package de.fh.rosenheim.aline.model.dtos.seminar;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SeminarBasicsDTO {

    @JsonView(View.SeminarBasicsView.class)
    private String name;

    @JsonView(View.SeminarBasicsView.class)
    private String description;

    @JsonView(View.SeminarBasicsView.class)
    private String agenda;

    @JsonView(View.SeminarBasicsView.class)
    private boolean bookable;

    @JsonView(View.SeminarBasicsView.class)
    @ApiModelProperty(required = true)
    private String category;

    @JsonView(View.SeminarBasicsView.class)
    private int[] targetLevel;

    @JsonView(View.SeminarBasicsView.class)
    private String requirements;

    @JsonView(View.SeminarBasicsView.class)
    private String trainer;

    @JsonView(View.SeminarBasicsView.class)
    private String contactPerson;

    @JsonView(View.SeminarBasicsView.class)
    private String trainingType;

    @JsonView(View.SeminarBasicsView.class)
    private int maximumParticipants;

    @JsonView(View.SeminarBasicsView.class)
    @ApiModelProperty(notes = SwaggerTexts.CURRENCY)
    private long costsPerParticipant;

    @JsonView(View.SeminarBasicsView.class)
    private String bookingTimelog;

    @JsonView(View.SeminarBasicsView.class)
    private String goal;

    @JsonView(View.SeminarBasicsView.class)
    private String duration;

    @JsonView(View.SeminarBasicsView.class)
    private String cycle;

    @JsonView(View.SeminarBasicsView.class)
    private Date[] dates;
}
