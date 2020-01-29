package com.example.demo.controllers;

import com.example.demo.config.JsonParser;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
import com.example.demo.services.exeption.IdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.NodeService;

@RestController
@RequestMapping("/api/node")
public class NodeController {
    private final NodeService nodeService;
    private Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Autowired
    NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping(path = "/create/{parentId}/{rootId}", consumes = "application/json")
    public ResponseEntity<String> createNode(@RequestBody String jsonNode, @PathVariable String parentId, @PathVariable String rootId) {
        Node node = JsonParser.toJavaObject(jsonNode);
        try {
            logger.info("Create node controller");
            nodeService.createNode(node, parentId, rootId);
        } catch (HierarchyException | IdNotFoundException h) {
            logger.error("Wrong data. Hierarchy is not valid or Id does not exist in database ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/delete/{nodeId}")
    public ResponseEntity<String> deleteNode(@PathVariable String nodeId) {
        try {
            logger.info("delete node controller");
            nodeService.deleteNode(nodeId);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (IdNotFoundException e){
            logger.error("Wrong data. Id does not exist in database ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(path = "/update/{parentNodeId}/{childNodeId}")
    public ResponseEntity<String> update(@PathVariable String parentNodeId, @PathVariable String childNodeId,
                                         @RequestBody String jsonNode) {
        Node node = JsonParser.toJavaObject(jsonNode);
        try {
            logger.info("update child controller");
            nodeService.updateNodeChild(parentNodeId, childNodeId, node);
        } catch (HierarchyException | IdNotFoundException h) {
            logger.error("Wrong data. Hierarchy is not valid or Id does not exist in database ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
