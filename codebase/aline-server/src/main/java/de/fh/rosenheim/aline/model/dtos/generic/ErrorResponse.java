package de.fh.rosenheim.aline.model.dtos.generic;

import de.fh.rosenheim.aline.model.base.ModelBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A simple error message
 */
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse extends ModelBase {

    private String message;
}
