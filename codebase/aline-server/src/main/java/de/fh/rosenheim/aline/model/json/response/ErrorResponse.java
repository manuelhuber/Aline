package de.fh.rosenheim.aline.model.json.response;

import de.fh.rosenheim.aline.model.base.ModelBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
/**
 * A simple error message
 */
public class ErrorResponse extends ModelBase {

    private String message;
}
