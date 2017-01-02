package de.fh.rosenheim.aline.model.json.response;

import de.fh.rosenheim.aline.model.domain.BookingStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserBookingDTO {

    private long id;

    @ApiModelProperty(position = 1)
    private BookingStatus status;

    @ApiModelProperty(position = 2)
    private long seminarId;

    @ApiModelProperty(position = 3)
    private String seminarName;

    @ApiModelProperty(position = 4)
    private int seminarYear;

    @ApiModelProperty(position = 5)
    private int seminarCost;

    @ApiModelProperty(position = 6)
    private Date created;

    @ApiModelProperty(position = 7)
    private Date updated;
}
