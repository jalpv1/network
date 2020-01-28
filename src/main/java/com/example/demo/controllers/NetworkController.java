package com.example.demo.controllers;

import com.example.demo.config.JsonParser;
import com.example.demo.dao.NetworkRepository;
import com.example.demo.entity.Node;
import com.example.demo.services.NetworkService;
import com.example.demo.services.exeption.HierarchyException;
import com.example.demo.services.exeption.IdNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/network")
public class NetworkController {
    private final NetworkService networkService;
    Logger logger = LoggerFactory.getLogger(NetworkController.class);

    @Autowired
    NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @PostMapping(path = "/delete/{rootIdentifier}")
    public ResponseEntity<String> deleteNetwork(@PathVariable String rootIdentifier) {
        logger.info("Deleting network with id " + rootIdentifier);
        try {
            networkService.deleteNetwork(rootIdentifier);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (IdNotFoundException i){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<String> createNetwork(@RequestBody String nodeJson) {
        Node node = JsonParser.toJavaObject(nodeJson);

        try {
            networkService.createNetwork(node);
        } catch (HierarchyException |IdNotFoundException h) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping(path = "/update", consumes = "application/json")
    public ResponseEntity<String> updateNetwork(@RequestBody String nodeJson) {
        Node node = JsonParser.toJavaObject(nodeJson);
        try {
            networkService.updateNetwork(node);
        } catch (HierarchyException | IdNotFoundException h) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(path = "/search/root")
    public List<Node> searchInRootByName(@RequestParam String name) {

        logger.info("Info level log message");
        logger.debug("Debug level log message");
        logger.error("Error level log message");

        return networkService.searchInRootByName(name);
    }

    @GetMapping(path = "/search/node")
    public List<Node> searchInNodesByName(String name, String roodIdentifier) {
        try {
            return networkService.searchInNodesByName(name, roodIdentifier);

        } catch (IdNotFoundException h) {
           return new ArrayList<Node>();
        }
    }


}
