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
    public boolean validate(Node node,Node parentNode) throws HierarchyException {
        String type = node.getType().toUpperCase();
        //        //validateMap.get(node.getType().toUpperCase()).typeValid(node) &&
        //        //validateMap.get(node.getType().toUpperCase()).typeValid(node)
        //        //               ||childValid(node)
        //        //
        //        //               ||
        Validator v  = validateMap.get(type);
        boolean validation = v.hierarchyCheck(parentNode);
       if( !validation ||childValid(node)){
           throw new  HierarchyException ();
       }
       return true;
    }
}
