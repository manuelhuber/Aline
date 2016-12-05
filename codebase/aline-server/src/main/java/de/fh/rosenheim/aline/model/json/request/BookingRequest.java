package de.fh.rosenheim.aline.model.json.request;

import de.fh.rosenheim.aline.model.base.ModelBase;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookingRequest extends ModelBase {

    @NotNull
    private Long seminarId;

    private String userName;
}
