package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;

public class CreateSubstation implements Validator{
    @Override
    public boolean hierarchyCheck(Node parentNode) {
        return parentNode.getType().equalsIgnoreCase("Network");
               // || parentNode.getType().equalsIgnoreCase("Resource"));
    }
}
