package de.fh.rosenheim.aline.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UnknownCategoryException extends Exception {

    private List<String> allowedCategories;
}
