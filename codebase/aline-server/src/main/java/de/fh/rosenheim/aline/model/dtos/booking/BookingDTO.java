package de.fh.rosenheim.aline.model.dtos.booking;

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

    @JsonView(View.BookingSummaryView.class)
    private Long id;

    @JsonView(View.BookingSummaryView.class)
    @ApiModelProperty(position = 1)
    private BookingStatus status;

    @JsonView(View.BookingSummaryView.class)
    @ApiModelProperty(position = 2)
    private UserDTO user;

    @JsonView(View.BookingSummaryView.class)
    @ApiModelProperty(position = 3)
    private SeminarDTO seminar;

    @JsonView(View.BookingSummaryView.class)
    @ApiModelProperty(position = 4)
    private Date created;
    @JsonView(View.BookingSummaryView.class)
    @ApiModelProperty(position = 5)
    private Date updated;
}
