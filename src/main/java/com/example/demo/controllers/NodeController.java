package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.NodeService;
@RestController
@RequestMapping("/api")
public class NodeController {
    private final NodeService nodeService;
    @Autowired
    NodeController(NodeService nodeService){
        this.nodeService = nodeService;
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void createNode(@RequestParam String type, @RequestParam String name,@RequestParam String description
    ,@RequestParam int root_id,@RequestParam int parent_id){
        nodeService.createNode(type,name,description,root_id,parent_id);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteNode(@RequestParam int node_id){
        nodeService.deleteNode(node_id);
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestParam int oldNode_id,String newType, String newName, String newDescription,
                       int root_id, int parent_id){
        nodeService.updateNode(oldNode_id,newType, newName, newDescription, root_id, parent_id);
    }


}
