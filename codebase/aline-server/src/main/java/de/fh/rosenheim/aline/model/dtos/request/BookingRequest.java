package de.fh.rosenheim.aline.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.fh.rosenheim.aline.model.base.ModelBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookingRequest extends ModelBase {

    @NotNull
    @JsonProperty(value = "seminarId", required = true)
    @ApiModelProperty(required = true)
    private Long seminarId;

    private String userName;
}
