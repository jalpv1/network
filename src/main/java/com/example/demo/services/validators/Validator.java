package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;

public interface Validator  {
    default  boolean  typeValid (Node node) {
        return !node.getType().trim().isEmpty();
    }

    default boolean hierarchyCheck(Node parentNode)  {
        return  ! parentNode.getType().equalsIgnoreCase("Resource") ;
    }
}