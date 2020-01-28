package com.example.demo.services;

import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
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
    public void createNode(Node node, String parentId, String rootId) throws HierarchyException {
        int parentIdDB = nodeRepository.getIdByidentifier(parentId);
        int rootIdDB = nodeRepository.getIdByidentifier(rootId);

        nodeRepository.createNode(node, parentIdDB, rootIdDB);
    }

    @Transactional
    public void deleteNode(String nodeId) {
        nodeRepository.deleteNode(nodeId);
    }

    public void updateNodeChild(String parentNodeIdentifier, String childNodeIdentifier,
                                Node node) throws HierarchyException {
        nodeRepository.updateChild(parentNodeIdentifier, childNodeIdentifier, node);
    }
}
