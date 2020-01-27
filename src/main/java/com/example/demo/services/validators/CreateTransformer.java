package com.example.demo.services.validators;

import com.example.demo.entity.Node;

public class CreateTransformer implements Validator{
    @Override
    public boolean hierarchyCheck(Node parentNode) {
        return parentNode.getType().equalsIgnoreCase("Network") ||
                parentNode.getType().equalsIgnoreCase("Substation")&&
                        ! parentNode.getType().equalsIgnoreCase("Resource");
    }
}
