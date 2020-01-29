package com.example.demo.services;

import com.example.demo.dao.NetworkRepository;
import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
import com.example.demo.services.exeption.IdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NetworkService {
    private final NetworkRepository networkRepository;
    private final NodeRepository nodeRepository;
    Logger logger = LoggerFactory.getLogger(NetworkService.class);


    @Autowired
    public NetworkService(NetworkRepository networkRepository, NodeRepository nodeRepository) {
        this.networkRepository = networkRepository;
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public void deleteNetwork(String rootIdentifier) throws IdNotFoundException {
        logger.info("Delete network with id " + rootIdentifier);
        networkRepository.deleteNetwork(rootIdentifier);
    }

    @Transactional
    public void createNetwork(Node node) throws HierarchyException, IdNotFoundException {
        logger.info("create network with id " + node.getId());
        logger.info("create root with id " + node.getId());
        int rootId = nodeRepository.createNode(node);
        initParentId(node.getChildren(), node.getId());
        Queue<Node> nodes = new ArrayDeque<>(node.getChildren());
        logger.info("init Queue  with root's children ");
        while (!nodes.isEmpty()) {
            Node nodeFirstInQueue = Objects.requireNonNull(nodes.poll());
            logger.info("get first element of the Queue.");

            nodeRepository.createNode(nodeFirstInQueue, nodeFirstInQueue.getParentId(), rootId);
            logger.info("created first element of the Queue.");
            if (!nodeFirstInQueue.getChildren().isEmpty()) {
                logger.info("get children of first element of the Queue.");
                initParentId(nodeFirstInQueue.getChildren(), nodeFirstInQueue.getId());
                nodes.addAll(nodeFirstInQueue.getChildren());
            }
        }
    }

    @Transactional
    public void updateNetwork(Node node) throws HierarchyException, IdNotFoundException {
        logger.info("update network ");
        nodeRepository.updateRootNode(node);
        logger.info("update root node ");
        initParentId(node.getChildren(), node.getId());
        Queue<Node> nodes = new ArrayDeque<>(node.getChildren());
        logger.info("init Queue  with root's children ");
        while (!nodes.isEmpty()) {
            logger.info("get first element of the Queue.");
            Node nodeFirstInQueue = Objects.requireNonNull(nodes.poll());

            nodeRepository.updateChild(nodeRepository.getNodeById(nodeFirstInQueue.getParentId()).getId(),
                    nodeFirstInQueue.getId(), nodeFirstInQueue);
            logger.info("updated first element of the Queue.");

            if (!nodeFirstInQueue.getChildren().isEmpty()) {
                logger.info("get children of first element of the Queue.");
                initParentId(nodeFirstInQueue.getChildren(), nodeFirstInQueue.getId());
                nodes.addAll(nodeFirstInQueue.getChildren());
            }
        }
    }

    private void initParentId(List<Node> children, String parentIdentifier) throws IdNotFoundException {
        int parentIdDb = nodeRepository.getIdDBById(parentIdentifier);
        logger.info("get id of the parent");
        children.stream().peek((s) -> s.setParentId(parentIdDb)).collect(Collectors.toList());
    }

    public List<Node> searchInRootByName(String name) {
        logger.info("search in roots method ");

        return networkRepository.searchInRootByName(name);
    }

    public List<Node> searchInNodesByName(String name, String roodIdentifier) throws IdNotFoundException {
        logger.info("search in network method ");
        int rootId = nodeRepository.getIdDBById(roodIdentifier);
        return networkRepository.searchInNodesByName(name, rootId);
    }

}
