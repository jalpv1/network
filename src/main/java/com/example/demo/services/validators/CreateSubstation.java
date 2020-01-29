package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateSubstation implements Validator{
    Logger logger = LoggerFactory.getLogger(CreateSubstation.class);

    private  enum VALID_PARENTS {
        NETWORK;
    }
    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return parentNode.getType()
                .strip().equalsIgnoreCase(VALID_PARENTS.NETWORK.toString());
    }
}
