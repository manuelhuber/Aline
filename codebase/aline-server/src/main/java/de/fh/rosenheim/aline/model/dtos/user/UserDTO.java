package de.fh.rosenheim.aline.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.dtos.booking.BookingSummaryDTO;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    @JsonView(View.UserWithoutBookingView.class)
    String userName;

    @JsonView(View.UserWithoutBookingView.class)
    @ApiModelProperty(position = 1)
    String firstName;

    @JsonView(View.UserWithoutBookingView.class)
    @ApiModelProperty(position = 2)
    String lastName;

    @JsonView(View.UserWithoutBookingView.class)
    @ApiModelProperty(position = 3)
    String division;

    @JsonView(View.UserWithoutBookingView.class)
    @ApiModelProperty(position = 4)
    List<String> authorities;

    @ApiModelProperty(position = 5)
    List<BookingSummaryDTO> bookings;
}
