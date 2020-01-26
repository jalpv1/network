package com.example.demo.services;

import com.example.demo.dao.NetworkRepository;
import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NetworkService {
    private final NetworkRepository networkRepository;
    private final NodeRepository nodeRepository;


    @Autowired
    public NetworkService(NetworkRepository networkRepository, NodeRepository nodeRepository) {
        this.networkRepository = networkRepository;
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public void deleteNetwork(String rootIdentinier) {
        networkRepository.deleteNetwork(rootIdentinier);
    }

    public void createNetwork(Node node) {
        int rootId = node.getidDB();
        int parentId = node.getidDB();

        if (node.getChildren().size() != 0) {
            for (Node n : node.getChildren()) {
                createNetwork(n);
            }
        }
        nodeRepository.createNode(node, rootId, parentId);

    }
}
