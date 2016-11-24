package de.fh.rosenheim.aline.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NoObjectForIdException extends Exception {

    private Class object;
    private String id;

    public NoObjectForIdException(Class object, long id) {
        this.object = object;
        this.id = String.valueOf(id);
    }
}
