package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTransformer implements Validator{
    Logger logger = LoggerFactory.getLogger(CreateTransformer.class);

    private  enum VALID_PARENTS {
        NETWORK,
        SUBSTATION,
    }
    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return (parentNode.getType().strip().equalsIgnoreCase(VALID_PARENTS.NETWORK.toString()) ||
                parentNode.getType().strip().equalsIgnoreCase(VALID_PARENTS.SUBSTATION.toString()));
    }
}
