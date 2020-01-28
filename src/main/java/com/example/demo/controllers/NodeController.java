package com.example.demo.controllers;

import com.example.demo.config.JsonParser;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.NodeService;

@RestController
@RequestMapping("/api/node")
public class NodeController {
    private final NodeService nodeService;

    @Autowired
    NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    //@RequestMapping(value = "/create", method = RequestMethod.POST)
    @PostMapping(path = "/create/{parentId}/{rootId}", consumes = "application/json")
    public ResponseEntity<String> createNode(@RequestBody String jsonNode, @PathVariable int parentId, @PathVariable int rootId) {
        Node node = JsonParser.toJavaObject(jsonNode);
        try{
        nodeService.createNode(node, parentId, rootId);
        }
        catch (HierarchyException h){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.POST)
    public void deleteNode(@PathVariable int nodeId) {
        nodeService.deleteNode(nodeId);
    }

    @RequestMapping(value = "/update/{parentNodeIdentifier}/{childNodeIdentifier}", method = RequestMethod.POST)
    public ResponseEntity<String> update(@PathVariable String parentNodeIdentifier, @PathVariable String childNodeIdentifier,
                       @RequestBody String jsonNode) {
        Node node = JsonParser.toJavaObject(jsonNode);
        try{
        nodeService.updateNodeChild(parentNodeIdentifier, childNodeIdentifier, node);
        }
        catch (HierarchyException h){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
