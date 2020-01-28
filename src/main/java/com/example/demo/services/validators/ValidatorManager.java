package com.example.demo.services.validators;

import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
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
    private   boolean  childValid (Node node) {
        return node.getType().equalsIgnoreCase("Network");
    }
    //private N
    //private final static VALID_TYPES = [firstenum, secondendu thindenum]
    private   boolean  typeValid (Node node) {

     //   return node != null && VALID_TYPES.find(node)

        return !node.getType().trim().isEmpty() &&
                (node.getType().equalsIgnoreCase("Resource")
                        || (node.getType().equalsIgnoreCase("NetWork")
                        || node.getType().equalsIgnoreCase("TRANSFORMER")
                        || node.getType().equalsIgnoreCase("SUBSTATION")
                        || node.getType().equalsIgnoreCase("FEEDER")));
    }
    public void validate(Node node,Node parentNode) throws HierarchyException {
        String type = node.getType().toUpperCase();
        if(typeValid(node)) {
            Validator v = validateMap.get(type);
            boolean validation = validateMap.get(type).hierarchyCheck(parentNode);
            if (!validation || childValid(node)) {
                throw new HierarchyException();
            }
        }
        else {  throw new HierarchyException();}
    }
}
