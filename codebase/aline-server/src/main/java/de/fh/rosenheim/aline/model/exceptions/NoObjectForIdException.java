package de.fh.rosenheim.aline.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NoObjectForIdException extends Exception {

    private String id;

    public NoObjectForIdException(long id) {
        this.id = String.valueOf(id);
    }
}
