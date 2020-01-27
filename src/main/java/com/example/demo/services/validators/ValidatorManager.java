package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class ValidatorManager {
    private Map<String, Validator> validateMap;

    public ValidatorManager() {
        validateMap = new HashMap<>();
        validateMap.put("NETWORK", new CreateNetwork());
        validateMap.put("SUBSTATION", new CreateSubstation());
        validateMap.put("TRANSFORMER", new CreateTransformer());
        validateMap.put("FEEDER", new CreateFeeder());
        validateMap.put("RESOURCE", new CreateResource());
    }

    public boolean validate(Node node,Node parentNode) {
       return validateMap.get(node.getType().toUpperCase()).typeValid(node)
               && validateMap.get(node.getType().toUpperCase()).hierarchyCheck(parentNode);
    }
}
