package com.example.demo.services.validators;

import com.example.demo.entity.Node;

public class CreateResource implements Validator{
    @Override
    public boolean hierarchyCheck(Node parentNode) {
        return parentNode.getType().equalsIgnoreCase("Network") ||
                parentNode.getType().equalsIgnoreCase("Substation")  ||
                parentNode.getType().equalsIgnoreCase("Transformer") || 
                parentNode.getType().equalsIgnoreCase("Feeder") && 
                        ! parentNode.getType().equalsIgnoreCase("Resource");
    }
}
