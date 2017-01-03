package de.fh.rosenheim.aline.model.dtos.booking;

import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * A DTO that's specific to a user (an therefore does not contain info about users)
 */
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

    @ApiModelProperty(position = 5, notes = SwaggerTexts.CURRENCY)
    private long seminarCost;

    @ApiModelProperty(position = 6)
    private Date created;

    @ApiModelProperty(position = 7)
    private Date updated;
}
