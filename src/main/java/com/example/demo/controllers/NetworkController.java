package com.example.demo.controllers;

import com.example.demo.config.JsonParser;
import com.example.demo.entity.Node;
import com.example.demo.services.NetworkService;
import com.example.demo.services.NodeService;
import com.example.demo.services.exeption.HierarchyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/network")
public class NetworkController {
    private final NetworkService networkService;

    @Autowired
    NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }
    @RequestMapping(value = "/delete/{rootIdentifier}", method = RequestMethod.POST)
    public void deleteNetwork(@PathVariable String rootIdentifier) {
        networkService.deleteNetwork(rootIdentifier);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> createNetwork(@RequestBody String nodeJson) {
        Node node = JsonParser.toJavaObject(nodeJson);

        try {
            networkService.createNetwork(node);
        }
        catch (HierarchyException h){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateNetwork(@RequestBody String nodeJson) {
        Node node = JsonParser.toJavaObject(nodeJson);
        try {
        networkService.updateNetwork(node);
        }
        catch (HierarchyException h){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @RequestMapping(value = "/search/root", method = RequestMethod.GET)
    public List<Node> searchInRootByName(@RequestParam String name) {
        return networkService.searchInRootByName(name);
    }
    @RequestMapping(value = "/search/node", method = RequestMethod.GET)
    public List<Node> searchInNodesByName(String name, String roodIdentifier){
        return networkService.searchInNodesByName(name,roodIdentifier);
    }

}
