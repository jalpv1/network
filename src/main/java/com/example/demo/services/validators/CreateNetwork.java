package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateNetwork implements Validator {
    Logger logger = LoggerFactory.getLogger(CreateFeeder.class);

    private  enum NOT_VALID_PARENTS {
        NETWORK,
        SUBSTATION,
        TRANSFORMER,
        FEEDER;
    }
    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return !(parentNode.getType().strip().equalsIgnoreCase(NOT_VALID_PARENTS.NETWORK.toString()) ||
                parentNode.getType().strip().equalsIgnoreCase(NOT_VALID_PARENTS.SUBSTATION.toString())  ||
                parentNode.getType().strip().equalsIgnoreCase(NOT_VALID_PARENTS.TRANSFORMER.toString())||
                parentNode.getType().strip().equalsIgnoreCase(NOT_VALID_PARENTS.FEEDER.toString()) );
    }
}
