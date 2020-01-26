package com.example.demo.controllers;

import com.example.demo.config.JsonParser;
import com.example.demo.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.NodeService;
@RestController
@RequestMapping("/api")
public class NodeController {
    private final NodeService nodeService;
    private final JsonParser jsonParser;

    @Autowired
    NodeController(NodeService nodeService,JsonParser jsonParser){
        this.nodeService = nodeService;
        this.jsonParser = jsonParser;
    }
    //@RequestMapping(value = "/create", method = RequestMethod.POST)
    @PostMapping(path= "/create", consumes = "application/json")
    /*  public void createNode(@RequestParam String type, @RequestParam String name,@RequestParam String description
    ,@RequestParam int root_id,@RequestParam int parent_id){

        nodeService.createNode(type,name,description,root_id,parent_id);
    }

   */
    public void createNode(@RequestBody String json){
        Node node = jsonParser.toJavaObject(json);
        return;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteNode(@RequestParam int node_id){
        nodeService.deleteNode(node_id);
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestParam String parentNodeIdentifier,@RequestParam String childNodeIdentifier,
                       @RequestParam  String newType, @RequestParam String newName,@RequestParam  String newDescription){
        nodeService.updateNodeChild(parentNodeIdentifier,childNodeIdentifier,newType,newName,newDescription);
    }


}
