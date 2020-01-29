package com.example.demo.services;

import com.example.demo.dao.NodeRepository;
import com.example.demo.entity.Node;
import com.example.demo.services.exceptions.HierarchyException;
import com.example.demo.services.exceptions.IdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;
    private  Logger logger = LoggerFactory.getLogger(NodeService.class);

    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public void createNode(Node node, String parentId, String rootId) throws HierarchyException, IdNotFoundException {
        logger.info("Create node method. Parent id :" + parentId +" rootId "+ rootId);
        int parentIdDB = nodeRepository.getIdDBById(parentId);
        int rootIdDB = nodeRepository.getIdDBById(rootId);
        nodeRepository.createNode(node, parentIdDB, rootIdDB);
    }

    @Transactional
    public void deleteNode(String nodeId)throws IdNotFoundException{
        logger.info("Delete node method. Node id: " + nodeId);
        nodeRepository.deleteNode(nodeId);
    }

    public void updateNodeChild(String parentNodeIdentifier, String childNodeIdentifier,
                                Node node) throws HierarchyException,IdNotFoundException {
        logger.info("Update child method. Parent  id: " + parentNodeIdentifier +
                " Child id: " +childNodeIdentifier);
        nodeRepository.updateChild(parentNodeIdentifier, childNodeIdentifier, node);
    }
}
