package com.example.demo.dao;

import com.example.demo.dao.mappers.NodeMapper;
//import com.example.demo.dao.query.NodeQuery;
import com.example.demo.dao.*;

import com.example.demo.dao.query.NodeQuery;
import com.example.demo.entity.Node;
import com.example.demo.services.validators.ValidatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class NodeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ValidatorManager validatorManager;
    @Autowired
    public NodeRepository(JdbcTemplate jdbcTemplate,ValidatorManager validatorManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.validatorManager = validatorManager;
    }

    public boolean createNode(Node node, int parentId, int rootId) {
       if(validatorManager.validate(node,getNodeById(parentId))) {
           jdbcTemplate.update(NodeQuery.CREATE_NODE, node.getType(), node.getName(),
                   node.getDescription());
           int nodeId = jdbcTemplate.queryForObject(NodeQuery.GET_NODES_ID, Integer.class);
           createIdentifier(nodeId, node);
           if (!node.getParams().isEmpty()) {
               addParams(node.getParams(), nodeId);
           }
           addHierarchy(nodeId, parentId, rootId);
           return true;
      }
       return false;
    }

    public int createNode(Node node) {
        jdbcTemplate.update(NodeQuery.CREATE_NODE, node.getType(), node.getName(),
                node.getDescription());
        int nodeId = jdbcTemplate.queryForObject(NodeQuery.GET_NODES_ID, Integer.class);
        createIdentifier(nodeId, node);
        if (!node.getParams().isEmpty()) {
            addParams(node.getParams(), nodeId);
        }

        addHierarchy(nodeId, 0, nodeId);
        return nodeId;
    }

    private void createIdentifier(int id, Node node) {
        StringBuilder identifier = new StringBuilder(node.getType() + id);
        Object[] params = new Object[]{identifier, id};
        node.setIdentifier(identifier.toString());
        jdbcTemplate.update(NodeQuery.UPDATE_NODE_IDENTIFIER, params);
    }

    private void addParams(Map<String, String> params, int node_id) {
        for (Map.Entry<String, String> pair : params.entrySet()) {
            jdbcTemplate.update(NodeQuery.ADD_PARAMS, node_id, pair.getKey(), pair.getValue());
        }
    }

    private void updateParams(Map<String, String> newParams, int node_id) {
        for (Map.Entry<String, String> pair : newParams.entrySet()) {
            jdbcTemplate.update(NodeQuery.UPDATE_PARAMS, pair.getKey(), pair.getValue(), node_id, pair.getKey());
        }
    }

    private void addHierarchy(int node_id, int parent_id, int root_id) {
        jdbcTemplate.update(NodeQuery.ADD_HIERARCHY, node_id, parent_id, root_id);
    }

    public void deleteNode(int node_id) {
        deleteHierarchy(node_id);
        jdbcTemplate.update(NodeQuery.DELETE_NODE, node_id);
        jdbcTemplate.update(NodeQuery.DELETE_PARAMS, node_id);

        //jdbcTemplate.update(NodeQuery.DELETE_HIERARCHY, node_id, node_id, node_id);
    }

    private void deleteHierarchy(int node_id) {
        Integer parentId = jdbcTemplate.queryForObject(NodeQuery.SELECT_HIERARCHY_NODE_PARENT, Integer.class, node_id);
        Integer rootId = jdbcTemplate.queryForObject(NodeQuery.SELECT_HIERARCHY_NODE_ROOT, Integer.class, node_id);

        jdbcTemplate.update(NodeQuery.DELETE_HIERARCHY_NODE, node_id);
        List<Node> children = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NODE_CHILDREN, new Object[]{node_id}, new NodeMapper());
        for (Node child : children) {
            jdbcTemplate.update(NodeQuery.ADD_HIERARCHY, child.getIdDB(), parentId, rootId);
        }

        jdbcTemplate.update(NodeQuery.DELETE_HIERARCHY_CHILDREN, node_id);

    }

    public boolean updateChild(String parentNodeIdentifier, String childNodeIdentifier,
                               Node node) {

        Integer parentId = jdbcTemplate.queryForObject(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, parentNodeIdentifier);
        Integer childId = jdbcTemplate.queryForObject(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, childNodeIdentifier);
        //  int count =
        Object[] params = new Object[]{node.getType(), node.getName(), node.getDescription(), childNodeIdentifier, childId};
        if (Objects.requireNonNull(jdbcTemplate.queryForObject(NodeQuery.CHECK_HIERARCHY, Integer.class, parentId, childId)).equals(1)) {
            jdbcTemplate.update(NodeQuery.UPDATE_NODE, params);
            if (!node.getParams().isEmpty()) {
                updateParams(node.getParams(), childId);
            }
        }
        return false;
    }

    public boolean updateRootNode(Node node) {
        Object[] params =
                new Object[]{node.getType(), node.getName(), node.getDescription(), node.getIdentifier()};
        jdbcTemplate.update(NodeQuery.UPDATE_ROOT, params);
        if (!node.getParams().isEmpty()) {
            updateParams(node.getParams(), node.getIdDB());
        }

        return false;
    }

    public Integer getIdByidentifier(String identifier) {
        return jdbcTemplate.queryForObject(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, identifier);

    }

    public Node getNodeById(int id) {
        List<Node> node = jdbcTemplate.query(NodeQuery.SELECT_NODE_BY_ID, new Object[]{id}, new NodeMapper());
        if (node.isEmpty()) {
            return null;
        }
        return node.get(0);

    }


}
