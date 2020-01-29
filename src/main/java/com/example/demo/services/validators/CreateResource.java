package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateResource implements Validator{
    Logger logger = LoggerFactory.getLogger(CreateResource.class);

    private  enum VALID_PARENTS {
        NETWORK,
        SUBSTATION,
        TRANSFORMER,
        FEEDER;
    }
    @Override
    public boolean hierarchyIsValid(Node parentNode) {
        return parentNode.getType().strip().equalsIgnoreCase(VALID_PARENTS.NETWORK.toString()) ||
                parentNode.getType().strip().equalsIgnoreCase(VALID_PARENTS.SUBSTATION.toString())  ||
                parentNode.getType().strip().equalsIgnoreCase(VALID_PARENTS.TRANSFORMER.toString())||
                parentNode.getType().strip().equalsIgnoreCase(VALID_PARENTS.FEEDER.toString()) ;
    }
}
