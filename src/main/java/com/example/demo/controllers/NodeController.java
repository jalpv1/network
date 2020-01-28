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

    @PostMapping(path = "/create/{parentId}/{rootId}", consumes = "application/json")
    public ResponseEntity<String> createNode(@RequestBody String jsonNode, @PathVariable String parentId, @PathVariable String rootId) {
        Node node = JsonParser.toJavaObject(jsonNode);
        try {
            nodeService.createNode(node, parentId, rootId);
        } catch (HierarchyException h) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/delete/{nodeId}")
    public void deleteNode(@PathVariable String nodeId) {
        nodeService.deleteNode(nodeId);
    }

    @PostMapping(path = "/update/{parentNodeIdentifier}/{childNodeIdentifier}")
    public ResponseEntity<String> update(@PathVariable String parentNodeIdentifier, @PathVariable String childNodeIdentifier,
                                         @RequestBody String jsonNode) {
        Node node = JsonParser.toJavaObject(jsonNode);
        try {
            nodeService.updateNodeChild(parentNodeIdentifier, childNodeIdentifier, node);
        } catch (HierarchyException h) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
