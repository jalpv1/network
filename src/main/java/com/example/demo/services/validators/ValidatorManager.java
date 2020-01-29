package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.entity.NodeTypes;
import com.example.demo.services.exeption.HierarchyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Component
public class ValidatorManager {
    private Map<String, Validator> validateMap;
    private  Logger logger = LoggerFactory.getLogger(ValidatorManager.class);

    public ValidatorManager() {
        validateMap = new HashMap<>();
        validateMap.put(NodeTypes.NETWORK.toString(), new CreateNetwork());
        validateMap.put(NodeTypes.SUBSTATION.toString(), new CreateSubstation());
        validateMap.put(NodeTypes.TRANSFORMER.toString(), new CreateTransformer());
        validateMap.put(NodeTypes.FEEDER.toString(), new CreateFeeder());
        validateMap.put(NodeTypes.RESOURCE.toString(), new CreateResource());
    }
    private String[]validChidlren = {NodeTypes.RESOURCE.toString(), NodeTypes.NETWORK.toString(),
            NodeTypes.SUBSTATION.toString(),
            NodeTypes.TRANSFORMER.toString(), NodeTypes.FEEDER.toString()};
    private   boolean  typeIsValid (Node node) {
        logger.info("check that type is valid ");
        return Arrays.asList(validChidlren).contains(node.getType().strip().toUpperCase());
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
