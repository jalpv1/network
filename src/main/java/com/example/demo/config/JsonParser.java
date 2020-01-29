package com.example.demo.config;

import com.example.demo.entity.Node;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
@Component
public class JsonParser {
    public static Node toJavaObject(String json)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json,Node.class);

        }
        catch (Exception e){
            return new Node();
        }
    }

}
