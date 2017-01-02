package de.fh.rosenheim.aline.model.dtos.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookingSummaryDto {

    int year;
    @ApiModelProperty(position = 1)
    int totalSpending;
    @ApiModelProperty(position = 2)
    List<UserBookingDTO> bookings;
}
