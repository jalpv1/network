package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class ValidatorManager {
    private Map<String, Validator> validateMap;
    Logger logger = LoggerFactory.getLogger(ValidatorManager.class);

    public ValidatorManager() {
        validateMap = new HashMap<>();
        validateMap.put(VALID_TYPES.NETWORK.toString(), new CreateNetwork());
        validateMap.put(VALID_TYPES.SUBSTATION.toString(), new CreateSubstation());
        validateMap.put(VALID_TYPES.TRANSFORMER.toString(), new CreateTransformer());
        validateMap.put(VALID_TYPES.FEEDER.toString(), new CreateFeeder());
        validateMap.put(VALID_TYPES.RESOURCE.toString(), new CreateResource());
    }
    private  enum VALID_TYPES {
        NETWORK,
        SUBSTATION,
        TRANSFORMER,
        FEEDER,
        RESOURCE;
    }
    private   boolean  typeIsValid (Node node) {
        logger.info("check that type is valid ");
        return (node.getType().strip().equalsIgnoreCase(VALID_TYPES.RESOURCE.toString())
                        || (node.getType().strip().equalsIgnoreCase(VALID_TYPES.NETWORK.toString())
                        || node.getType().strip().equalsIgnoreCase(VALID_TYPES.TRANSFORMER.toString())
                        || node.getType().strip().equalsIgnoreCase(VALID_TYPES.SUBSTATION.toString())
                        || node.getType().strip().equalsIgnoreCase(VALID_TYPES.FEEDER.toString())));
    }
    public void validate(Node node,Node parentNode) throws HierarchyException {
        String type = node.getType().toUpperCase();
        if(!typeIsValid(node)) {
            logger.info("type is not valid ,throw HierarchyException ");
            throw new HierarchyException();
        }
        logger.info("Check hierarchy. Node's type is "+ node.getType());

        if (!validateMap.get(type).hierarchyIsValid(parentNode) ) {
            logger.info("hierarchy is not valid,throw HierarchyException ");
            throw new HierarchyException();
        }

    }
    public void validate(Node node) throws HierarchyException {
        logger.info("Check root node ");
        if(!typeIsValid(node)) {
            logger.info("type is not valid ,throw HierarchyException ");
            throw new HierarchyException();
        }
    }
}
