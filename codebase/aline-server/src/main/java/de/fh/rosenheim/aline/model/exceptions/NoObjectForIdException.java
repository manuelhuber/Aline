package de.fh.rosenheim.aline.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NoObjectForIdException extends Exception {

    private long id;
}
