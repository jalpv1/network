package com.example.demo.services.validators;

import com.example.demo.entity.Node;

public interface Validator {
    enum NOT_VALID_PARENTS {
        RECOURSE;
    }

    default boolean hierarchyIsValid(Node parentNode) {
        return !parentNode.getType().strip().equalsIgnoreCase(NOT_VALID_PARENTS.RECOURSE.toString());
    }
}
