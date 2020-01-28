package com.example.demo.dao;

import com.example.demo.dao.mappers.NodeMapper;
import com.example.demo.dao.query.NodeQuery;
import com.example.demo.entity.Node;
import com.example.demo.services.exeption.HierarchyException;
import com.example.demo.services.validators.ValidatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class NodeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ValidatorManager validatorManager;

    @Autowired
    public NodeRepository(JdbcTemplate jdbcTemplate, ValidatorManager validatorManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.validatorManager = validatorManager;
    }

    public void createNode(Node node, int parentId, int rootId) throws HierarchyException {
        Node parent = getNodeById(parentId);
        validatorManager.validate(node, parent);
        jdbcTemplate.update(NodeQuery.CREATE_NODE, node.getType(), node.getName(),
                node.getDescription());
        Integer nodeId = jdbcTemplate.queryForObject(NodeQuery.GET_NODES_ID, Integer.class);
        createIdentifier(nodeId, node);
        if (!node.getParams().isEmpty()) {
            addParams(node.getParams(), nodeId);
        }
        addHierarchy(nodeId, parentId, rootId);
    }

    public Integer createNode(Node node) {

        jdbcTemplate.update(NodeQuery.CREATE_NODE, node.getType(), node.getName(),
                node.getDescription());
        Integer nodeId = jdbcTemplate.queryForObject(NodeQuery.GET_NODES_ID, Integer.class);
        createIdentifier(nodeId, node);

        addHierarchy(nodeId, 0, nodeId);
        if (!node.getParams().isEmpty()) {
            addParams(node.getParams(), nodeId);

        }
        return nodeId;
    }


    private void createIdentifier(Integer id, Node node) {
        StringBuilder identifier = new StringBuilder(node.getType() + id);
        Object[] params = new Object[]{identifier, id};
        node.setIdentifier(identifier.toString());
        jdbcTemplate.update(NodeQuery.UPDATE_NODE_IDENTIFIER, params);
    }

    private void addParams(Map<String, String> params, Integer node_id) {
        for (Map.Entry<String, String> pair : params.entrySet()) {
            jdbcTemplate.update(NodeQuery.ADD_PARAMS, node_id, pair.getKey(), pair.getValue());
        }
    }

    private void updateParams(Map<String, String> newParams, Integer node_id) {
        for (Map.Entry<String, String> pair : newParams.entrySet()) {
            jdbcTemplate.update(NodeQuery.UPDATE_PARAMS, pair.getKey(), pair.getValue(), node_id, pair.getKey());
        }
    }

    private void addHierarchy(Integer node_id, Integer parent_id, Integer root_id) {
        jdbcTemplate.update(NodeQuery.ADD_HIERARCHY, node_id, parent_id, root_id);
    }

    public void deleteNode(String nodeIdentifier) {
        int nodeId = getIdByidentifier(nodeIdentifier);
        deleteHierarchy(nodeId);
        jdbcTemplate.update(NodeQuery.DELETE_NODE, nodeId);
        jdbcTemplate.update(NodeQuery.DELETE_PARAMS, nodeId);
    }

    private void deleteHierarchy(Integer node_id) {
        Integer parentId = jdbcTemplate.queryForObject(NodeQuery.SELECT_HIERARCHY_NODE_PARENT, Integer.class, node_id);
        Integer rootId = jdbcTemplate.queryForObject(NodeQuery.SELECT_HIERARCHY_NODE_ROOT, Integer.class, node_id);

        jdbcTemplate.update(NodeQuery.DELETE_HIERARCHY_NODE, node_id);
        List<Node> children = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NODE_CHILDREN, new Object[]{node_id}, new NodeMapper());
        for (Node child : children) {
            jdbcTemplate.update(NodeQuery.ADD_HIERARCHY, child.getIdDB(), parentId, rootId);
        }

        jdbcTemplate.update(NodeQuery.DELETE_HIERARCHY_CHILDREN, node_id);

    }

    public void updateChild(String parentNodeIdentifier, String childNodeIdentifier,
                            Node node) throws HierarchyException {

        Integer parentId = jdbcTemplate.queryForObject(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, parentNodeIdentifier);
        Integer childId = jdbcTemplate.queryForObject(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, childNodeIdentifier);
        Node parent = getNodeById(getIdByidentifier(parentNodeIdentifier));
        validatorManager.validate(node, parent);
        Object[] params = new Object[]{node.getType(), node.getName(), node.getDescription(), childNodeIdentifier, childId};
        if (Objects.requireNonNull(jdbcTemplate.queryForObject(NodeQuery.CHECK_HIERARCHY, Integer.class, parentId, childId)).equals(1)) {
            jdbcTemplate.update(NodeQuery.UPDATE_NODE, params);
            if (!node.getParams().isEmpty()) {
                updateParams(node.getParams(), childId);
            }
        }
    }

    public void updateRootNode(Node node) {
        Object[] params =
                new Object[]{node.getType(), node.getName(), node.getDescription(), node.getIdentifier()};
        jdbcTemplate.update(NodeQuery.UPDATE_ROOT, params);
        if (!node.getParams().isEmpty()) {
            updateParams(node.getParams(), node.getIdDB());
        }

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

     void getParams(Node node) {
        List<Map<String, Object>> map =
                jdbcTemplate.queryForList(NodeQuery.GET_PARAMS, node.getIdDB());
        Map<String, String> params = new HashMap<>();
        for (Map<String, Object> m : map) {
            for (Map.Entry<String, Object> pair : m.entrySet()) {
                params.put(pair.getKey(), (String) pair.getValue());
            }

        }

        node.setParams(params);


    }


}
