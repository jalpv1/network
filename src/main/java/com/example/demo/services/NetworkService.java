package com.example.demo.services;

import com.example.demo.dao.NetworkRepository;
import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
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
    public void createNetwork(Node node) {
        //создади корень и запомнили айди
        int rootId = nodeRepository.createNode(node, 0, 0);
        initParentId(node.getChildren(), node.getIdentifier());
        // инициализировали детьми корня очередь
        Queue<Node> nodes = new ArrayDeque<>(node.getChildren());
        //если у него есть дети то и очередь полная
        while (!nodes.isEmpty()) {
            //создаем єлемент удаляя его из очереди
            Node nodeFirstInQueue = Objects.requireNonNull(nodes.poll());
            nodeRepository.createNode(nodeFirstInQueue, nodeFirstInQueue.getParentId(), rootId);
            //берем всех детей первого єлемента и вставляем их в очередь
            if (!nodeFirstInQueue.getChildren().isEmpty()) {
                initParentId(nodeFirstInQueue.getChildren(), nodeFirstInQueue.getIdentifier());
                nodes.addAll(nodeFirstInQueue.getChildren());

            }
            //но следующим может біть его брат!!!
            nodeRepository.createNode(nodeFirstInQueue, nodeFirstInQueue.getParentId(), rootId);

        }
    }

    public void initParentId(List<Node> children, String parentIdentifier) {
        int parentIdDb = nodeRepository.getIdByidentifier(parentIdentifier);
        for (Node child : children) {
            child.setParentId(parentIdDb);
        }
    }


}
