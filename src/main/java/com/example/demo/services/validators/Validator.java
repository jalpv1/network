package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.entity.NodeTypes;

public interface Validator {
    default boolean hierarchyIsValid(Node parentNode) {
        return !parentNode.getType().strip().equalsIgnoreCase(NodeTypes.RESOURCE.toString());
    }
}
