package com.example.demo.dao;

import com.example.demo.dao.mappers.NodeMapper;
import com.example.demo.dao.query.NodeQuery;
import com.example.demo.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NetworkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NetworkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void deleteNetwork(String rootIdentifier){
        Integer rootId = jdbcTemplate.queryForObject(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, rootIdentifier);
        List<Node> networkNodes = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NETWORK_BY_ROOT, new Object[]{rootId}, new NodeMapper());
        for (Node node:networkNodes) {
            jdbcTemplate.update(NodeQuery.DELETE_NODE, node.getIdDB());

        }
    }
}
