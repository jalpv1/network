package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.entity.NodeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CreateNetwork implements Validator {

    private String[]types = {NodeTypes.RESOURCE.toString(), NodeTypes.NETWORK.toString(),
            NodeTypes.SUBSTATION.toString(),
            NodeTypes.TRANSFORMER.toString(), NodeTypes.FEEDER.toString()};

    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return !(Arrays.asList(types).contains(parentNode.getType().strip().toUpperCase()));
    }
}
