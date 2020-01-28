package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;

public class CreateTransformer implements Validator{
    @Override
    public boolean hierarchyCheck(Node parentNode) {
        boolean f1 = parentNode.getType().equalsIgnoreCase("Network");
        boolean f2 =parentNode.getType().equalsIgnoreCase("Substation");
        boolean f3 = parentNode.getType().equalsIgnoreCase("Resource");
        return (f1 ||
                f2 );
    }
}
