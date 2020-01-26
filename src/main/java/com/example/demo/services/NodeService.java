package com.example.demo.services;

import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;

    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }
    @Transactional
    public void createNode(String type, String name, String description, int root_id, int parent_id) {
        Node node = new Node();
        node.setName(name);
        node.setType(type);
        node.setDescription(description);
        nodeRepository.createNode(node, root_id, parent_id);
    }

    @Transactional
    public void deleteNode(int node_id) {
        nodeRepository.deleteNode(node_id);
    }

    @Transactional
    public void updateNode(int oldNode_id, String newType, String newName, String newDescription,
                           int root_id, int parent_id) {
        deleteNode(oldNode_id);
        createNode(newType, newName, newDescription, root_id, parent_id);

    }
    public boolean updateNodeChild(String parentNodeIdentifier,String childNodeIdentifier,
                String newType, String newName, String newDescription) {
     return nodeRepository.updateChild(parentNodeIdentifier,childNodeIdentifier,newType,newName,newDescription);
    }
}
