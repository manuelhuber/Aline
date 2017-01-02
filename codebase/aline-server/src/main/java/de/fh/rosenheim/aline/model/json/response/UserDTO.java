package de.fh.rosenheim.aline.model.json.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    String userName;

    @ApiModelProperty(position = 1)
    String firstName;

    @ApiModelProperty(position = 2)
    String lastName;

    @ApiModelProperty(position = 3)
    String division;

    @ApiModelProperty(position = 4)
    List<String> authorities;

    @ApiModelProperty(position = 5)
    List<BookingSummaryDto> bookings;
}
