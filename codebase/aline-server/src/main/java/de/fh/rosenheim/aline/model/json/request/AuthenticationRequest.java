package de.fh.rosenheim.aline.model.json.request;

import de.fh.rosenheim.aline.model.base.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest extends ModelBase {

    private static final long serialVersionUID = 6624726180748515507L;
    private String username;
    private String password;
}
