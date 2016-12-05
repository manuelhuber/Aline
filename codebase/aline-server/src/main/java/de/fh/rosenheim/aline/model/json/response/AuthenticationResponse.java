package de.fh.rosenheim.aline.model.json.response;

import de.fh.rosenheim.aline.model.base.ModelBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse extends ModelBase {

    private static final long serialVersionUID = -6624726180748515507L;
    private String token;
    private String[] authorities;
}
