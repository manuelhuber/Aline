package de.fh.rosenheim.aline.model.dtos.booking;

import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * The summary of bookings for a single user in a single year
 */
@Data
public class BookingSummaryDTO {

    private int year;
    @ApiModelProperty(position = 1, notes = SwaggerTexts.CURRENCY)
    private long totalSpending;
    @ApiModelProperty(position = 2)
    List<UserBookingDTO> bookings;
}
