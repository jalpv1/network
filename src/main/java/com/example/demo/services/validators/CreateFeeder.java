package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.entity.NodeTypes;
import com.example.demo.services.exeption.HierarchyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CreateFeeder implements Validator {

    private String[]validParents = { NodeTypes.NETWORK.toString(),
            NodeTypes.SUBSTATION.toString(),
            NodeTypes.TRANSFORMER.toString()};

    @Override
    public boolean hierarchyIsValid(Node parentNode){
        return Arrays.asList(validParents).contains(parentNode.getType().strip().toUpperCase());

    }
}
