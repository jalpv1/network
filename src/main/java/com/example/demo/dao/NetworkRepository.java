package com.example.demo.dao;

import com.example.demo.dao.mappers.NodeMapper;
import com.example.demo.dao.query.NodeQuery;
import com.example.demo.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NetworkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NodeRepository nodeRepository;

    @Autowired
    public NetworkRepository(JdbcTemplate jdbcTemplate, NodeRepository nodeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.nodeRepository = nodeRepository;
    }

    public void deleteNetwork(String rootIdentifier) {
        Integer rootId = jdbcTemplate.queryForObject(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, rootIdentifier);
        List<Node> networkNodes = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NETWORK_BY_ROOT, new Object[]{rootId}, new NodeMapper());
        for (Node node : networkNodes) {
            jdbcTemplate.update(NodeQuery.DELETE_NODE, node.getIdDB());

        }
    }

    public List<Node> searchInRootByName(String name) {
        List<Node> nodes =
                jdbcTemplate.query(NodeQuery.SELECT_ROOTS + NodeQuery.CONDITION_SEARCH_BY_NAME, new Object[]{"%" + name + "%"}, new NodeMapper());
        for (Node node : nodes) {
            nodeRepository.getParams(node);
        }
        return nodes;
    }

    public List<Node> searchInNodesByName(String name, int rootId) {
        List<Node> nodes = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NETWORK_BY_ROOT + NodeQuery.CONDITION_SEARCH_BY_NAME_NODE, new Object[]{rootId, "%" + name + "%"}, new NodeMapper());
        for (Node node : nodes) {
            nodeRepository.getParams(node);
        }
        return nodes;

    }
}
