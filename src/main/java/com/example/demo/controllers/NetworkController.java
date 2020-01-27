package com.example.demo.controllers;

import com.example.demo.config.JsonParser;
import com.example.demo.entity.Node;
import com.example.demo.services.NetworkService;
import com.example.demo.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void createNetwork(@RequestBody String nodeJson) {
        Node node = JsonParser.toJavaObject(nodeJson);
        networkService.createNetwork(node);
    }



}
