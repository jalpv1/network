package com.example.demo.services;

import com.example.demo.dao.NetworkRepository;
import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
import com.example.demo.services.exeption.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public void deleteNetwork(String rootIdentifier) throws IdNotFoundException {
        networkRepository.deleteNetwork(rootIdentifier);
    }

    @Transactional
    public void createNetwork(Node node) throws HierarchyException ,IdNotFoundException {
        int rootId = nodeRepository.createNode(node);
                initParentId (node.getChildren(), node.getId());
        Queue<Node> nodes = new ArrayDeque<>(node.getChildren());
        while (!nodes.isEmpty()) {
            Node nodeFirstInQueue = Objects.requireNonNull(nodes.poll());
                nodeRepository.createNode(nodeFirstInQueue, nodeFirstInQueue.getParentId(), rootId);
            if (!nodeFirstInQueue.getChildren().isEmpty()) {
                initParentId(nodeFirstInQueue.getChildren(), nodeFirstInQueue.getId());
                nodes.addAll(nodeFirstInQueue.getChildren());
            }
        }
    }

    @Transactional
    public void updateNetwork(Node node) throws HierarchyException, IdNotFoundException {
        nodeRepository.updateRootNode(node);
        initParentId(node.getChildren(), node.getId());
        Queue<Node> nodes = new ArrayDeque<>(node.getChildren());
        while (!nodes.isEmpty()) {
            Node nodeFirstInQueue = Objects.requireNonNull(nodes.poll());

            nodeRepository.updateChild(nodeRepository.getNodeById(nodeFirstInQueue.getParentId()).getId(),
                    nodeFirstInQueue.getId(), nodeFirstInQueue);
            if (!nodeFirstInQueue.getChildren().isEmpty()) {
                initParentId(nodeFirstInQueue.getChildren(), nodeFirstInQueue.getId());
                nodes.addAll(nodeFirstInQueue.getChildren());
            }
        }
    }

    private void initParentId(List<Node> children, String parentIdentifier) throws IdNotFoundException  {
        int parentIdDb = nodeRepository.getIdDBById(parentIdentifier);
        for (Node child : children) {
            child.setParentId(parentIdDb);
        }
    }

    public List<Node> searchInRootByName(String name) {
        return networkRepository.searchInRootByName(name);
    }

    public List<Node> searchInNodesByName(String name, String roodIdentifier) throws IdNotFoundException {
        int rootId = nodeRepository.getIdDBById(roodIdentifier);
        return networkRepository.searchInNodesByName(name,rootId);
    }

}
