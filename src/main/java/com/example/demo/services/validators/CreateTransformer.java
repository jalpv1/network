package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.entity.NodeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CreateTransformer implements Validator{
    private String[]validParenrs = { NodeTypes.NETWORK.toString(),
            NodeTypes.SUBSTATION.toString()};
    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return Arrays.asList(validParenrs).contains(parentNode.getType().strip().toUpperCase());
    }
}
