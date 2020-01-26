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


    @Autowired
    NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    //@RequestMapping(value = "/create", method = RequestMethod.POST)
    @PostMapping(path = "/create", consumes = "application/json/{parentId}/{rootId}")
    public void createNode(@RequestBody String jsonNode, @PathVariable int parentId, @PathVariable int rootId) {
        Node node = JsonParser.toJavaObject(jsonNode);
        nodeService.createNode(node, parentId, rootId);
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.POST)
    public void deleteNode(@PathVariable int nodeId) {
        nodeService.deleteNode(nodeId);
    }

    @RequestMapping(value = "/update/{parentNodeIdentifier}/{childNodeIdentifier}", method = RequestMethod.POST)
    public void update(@PathVariable String parentNodeIdentifier, @PathVariable String childNodeIdentifier,
                       @RequestBody String jsonNode) {
        Node node = JsonParser.toJavaObject(jsonNode);

        nodeService.updateNodeChild(parentNodeIdentifier, childNodeIdentifier, node);
    }


}
