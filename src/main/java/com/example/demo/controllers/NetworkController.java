package com.example.demo.controllers;

import com.example.demo.services.NetworkService;
import com.example.demo.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


}
