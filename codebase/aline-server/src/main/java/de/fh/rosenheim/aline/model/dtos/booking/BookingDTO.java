package de.fh.rosenheim.aline.model.dtos.booking;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BookingDTO {

    @JsonView(View.BookingOverview.class)
    private Long id;

    @JsonView(View.BookingOverview.class)
    @ApiModelProperty(position = 1)
    private BookingStatus status;

    @JsonView(View.BookingOverview.class)
    @ApiModelProperty(position = 2)
    private UserDTO user;

    @JsonView(View.BookingOverview.class)
    @ApiModelProperty(position = 3)
    // When serializing, only use reference
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "seminarId", required = true)
    private SeminarDTO seminar;

    @JsonView(View.BookingOverview.class)
    @ApiModelProperty(position = 4)
    private Date created;
    @JsonView(View.BookingOverview.class)
    @ApiModelProperty(position = 5)
    private Date updated;
}
