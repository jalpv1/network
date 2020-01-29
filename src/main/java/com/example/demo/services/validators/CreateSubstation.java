package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.entity.NodeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateSubstation implements Validator{
    private String[]validParents = { NodeTypes.NETWORK.toString()};
    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return Arrays.asList(validParents).contains(parentNode.getType()
                .strip().toUpperCase());
    }
}
