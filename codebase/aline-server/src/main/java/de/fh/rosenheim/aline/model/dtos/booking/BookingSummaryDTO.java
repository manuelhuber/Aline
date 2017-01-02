package de.fh.rosenheim.aline.model.dtos.booking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookingSummaryDTO {

    int year;
    @ApiModelProperty(position = 1)
    int totalSpending;
    @ApiModelProperty(position = 2)
    List<UserBookingDTO> bookings;
}
