package de.fh.rosenheim.aline.model.dtos.authentication;

import de.fh.rosenheim.aline.model.base.ModelBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDTO extends ModelBase {

    private static final long serialVersionUID = 6624726180748515507L;
    @ApiModelProperty(required = true)
    private String username;
    @ApiModelProperty(required = true)
    private String password;
}
