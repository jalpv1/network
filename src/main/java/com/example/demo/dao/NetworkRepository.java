package com.example.demo.dao;

import com.example.demo.controllers.NetworkController;
import com.example.demo.dao.mappers.NodeMapper;
import com.example.demo.dao.query.NodeQuery;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.IdNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NetworkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NodeRepository nodeRepository;
    private Logger logger = LoggerFactory.getLogger(NetworkRepository.class);

    @Autowired
    public NetworkRepository(JdbcTemplate jdbcTemplate, NodeRepository nodeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.nodeRepository = nodeRepository;
    }

    public void deleteNetwork(String rootIdentifier) throws IdNotFoundException {
        Integer rootId = nodeRepository.getIdDBById(rootIdentifier);
        List<Node> networkNodes = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NETWORK_BY_ROOT, new Object[]{rootId}, new NodeMapper());
       logger.debug("get nodes of network");
        for (Node node : networkNodes) {
            jdbcTemplate.update(NodeQuery.DELETE_NODE, node.getIdDB());
            logger.debug("delete nodes of network");


        }
        logger.info("network deleted ");

    }

    public List<Node> searchInRootByName(String name) {
        List<Node> nodes =
                jdbcTemplate.query(NodeQuery.SELECT_ROOTS + NodeQuery.CONDITION_SEARCH_BY_NAME, new Object[]{"%" + name + "%"}, new NodeMapper());
        nodes.stream().peek(nodeRepository::getParams).collect(Collectors.toList());
        logger.info("search successfully");

        return nodes;
    }

    public List<Node> searchInNodesByName(String name, int rootId) {
        List<Node> nodes = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NETWORK_BY_ROOT + NodeQuery.CONDITION_SEARCH_BY_NAME_NODE, new Object[]{rootId, "%" + name + "%"}, new NodeMapper());
        nodes.stream().peek(nodeRepository::getParams).collect(Collectors.toList());
        logger.info("search successfully");

        return nodes;

    }
}
