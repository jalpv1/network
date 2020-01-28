package com.example.demo.services;

import com.example.demo.dao.NetworkRepository;
import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
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
    public void deleteNetwork(String rootIdentinier) {
        networkRepository.deleteNetwork(rootIdentinier);
    }

    public void createNetworkREc(Node node) {
        //   int rootId = node.getidDB();
        //int parentId = node.getidDB();

        if (node.getChildren().size() != 0) {
            for (Node n : node.getChildren()) {
                createNetworkREc(n);
            }
        }
        // nodeRepository.createNode(node, rootId, parentId);

    }

    @Transactional
    public void createNetwork(Node node) throws HierarchyException {
       // int rootId = nodeRepository.createNode(node, 0, 0);
        int rootId = nodeRepository.createNode(node);
                initParentId(node.getChildren(), node.getIdentifier());
        Queue<Node> nodes = new ArrayDeque<>(node.getChildren());
        while (!nodes.isEmpty()) {
            Node nodeFirstInQueue = Objects.requireNonNull(nodes.poll());
            try {
                nodeRepository.createNode(nodeFirstInQueue, nodeFirstInQueue.getParentId(), rootId);

            }
            catch (HierarchyException h){
                throw new HierarchyException();
            }
            if (!nodeFirstInQueue.getChildren().isEmpty()) {
                initParentId(nodeFirstInQueue.getChildren(), nodeFirstInQueue.getIdentifier());
                nodes.addAll(nodeFirstInQueue.getChildren());
            }
            //nodeRepository.createNode(nodeFirstInQueue, nodeFirstInQueue.getParentId(), rootId);
        }
    }

    @Transactional
    public void updateNetwork(Node node) throws HierarchyException{
        nodeRepository.updateRootNode(node);
        initParentId(node.getChildren(), node.getIdentifier());
        Queue<Node> nodes = new ArrayDeque<>(node.getChildren());
        while (!nodes.isEmpty()) {
            Node nodeFirstInQueue = Objects.requireNonNull(nodes.poll());

            nodeRepository.updateChild(nodeRepository.getNodeById(nodeFirstInQueue.getParentId()).getIdentifier(),
                    nodeFirstInQueue.getIdentifier(), nodeFirstInQueue);
            if (!nodeFirstInQueue.getChildren().isEmpty()) {
                initParentId(nodeFirstInQueue.getChildren(), nodeFirstInQueue.getIdentifier());
                nodes.addAll(nodeFirstInQueue.getChildren());
            }
        }
    }

    private void initParentId(List<Node> children, String parentIdentifier) {
        int parentIdDb = nodeRepository.getIdByidentifier(parentIdentifier);
        for (Node child : children) {
            child.setParentId(parentIdDb);
        }
    }

    public List<Node> searchInRootByName(String name) {
        return networkRepository.searchInRootByName(name);
    }

    public List<Node> searchInNodesByName(String name, String roodIdentifier) {
        int rootId = nodeRepository.getIdByidentifier(roodIdentifier);
        return networkRepository.searchInNodesByName(name,rootId);
    }

}
