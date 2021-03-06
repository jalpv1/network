package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.entity.NodeTypes;
import java.util.Arrays;

public class CreateResource implements Validator{
    private String[]validParents = {NodeTypes.NETWORK.toString(),
            NodeTypes.SUBSTATION.toString(),
            NodeTypes.TRANSFORMER.toString(), NodeTypes.FEEDER.toString()};
    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return Arrays.asList(validParents).contains(parentNode.getType().strip().toUpperCase()) ;
    }
}
