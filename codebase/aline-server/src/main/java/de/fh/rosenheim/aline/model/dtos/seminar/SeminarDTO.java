package de.fh.rosenheim.aline.model.dtos.seminar;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SeminarDTO extends SeminarBasicsDTO {

    @JsonView(View.SeminarView.class)
    private long id;

    @JsonView(View.SeminarView.class)
    @ApiModelProperty(notes = SwaggerTexts.ACTIVE_BOOKINGS)
    private int activeBookings;

    @JsonView(View.SeminarView.class)
    private Date created;

    @JsonView(View.SeminarView.class)
    private boolean billGenerated;

    @JsonView(View.SeminarView.class)
    private Date updated;
}
