package de.fh.rosenheim.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NoObjectForIdException extends Exception {

    private long id;
}
