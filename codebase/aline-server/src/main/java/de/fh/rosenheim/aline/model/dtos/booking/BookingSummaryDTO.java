package de.fh.rosenheim.aline.model.dtos.booking;

import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * The summary of bookings for a single user in a single year
 */
@Data
@Builder
public class BookingSummaryDTO {

    private int year;
    @ApiModelProperty(position = 1, notes = SwaggerTexts.PLANNED_SPENDING)
    private long plannedSpending;
    @ApiModelProperty(position = 2, notes = SwaggerTexts.GRANTED_SPENDING)
    private long grantedSpending;
    @ApiModelProperty(position = 3)
    List<UserBookingDTO> bookings;
}
