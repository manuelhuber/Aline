package de.fh.rosenheim.model.json.response;

import de.fh.rosenheim.model.base.ModelBase;
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
