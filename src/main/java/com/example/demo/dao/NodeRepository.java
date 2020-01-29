package com.example.demo.dao;

        import com.example.demo.dao.mappers.NodeMapper;
        import com.example.demo.dao.query.NodeQuery;
        import com.example.demo.entity.Node;
        import com.example.demo.services.exeption.HierarchyException;
        import com.example.demo.services.exeption.IdNotFoundException;
        import com.example.demo.services.validators.ValidatorManager;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.stereotype.Repository;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Objects;

@Repository
public class NodeRepository {
    private Logger logger = LoggerFactory.getLogger(NodeRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final ValidatorManager validatorManager;

    @Autowired
    public NodeRepository(JdbcTemplate jdbcTemplate, ValidatorManager validatorManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.validatorManager = validatorManager;
    }

    public void createNode(Node node, int parentId, int rootId) throws HierarchyException {
        logger.debug("Create node method. Parent id in dataBase: " + parentId + "Parent id in dataBase: " + rootId);
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

    public Integer createNode(Node node) throws HierarchyException {
        logger.info("Create root node method. ");
        validatorManager.validate(node);
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
        logger.info("Create id of the node method. Id in database " + id);
        StringBuilder identifier = new StringBuilder(node.getType() + id);
        Object[] params = new Object[]{identifier, id};
        node.setId(identifier.toString());
        jdbcTemplate.update(NodeQuery.UPDATE_NODE_IDENTIFIER, params);
        logger.info(" id created successfully. Id is" + identifier);

    }

    private void addParams(Map<String, String> params, Integer nodeId) {
        logger.info("Create extra params  method. Id in database " + nodeId);
        for (Map.Entry<String, String> pair : params.entrySet()) {
            jdbcTemplate.update(NodeQuery.ADD_PARAMS, nodeId, pair.getKey(), pair.getValue());
        }
        logger.info("extra params  created successfully ");

    }

    private void updateParams(Map<String, String> newParams, Integer nodeId) {
        logger.info("update extra params  method. Id in database " + nodeId);
        for (Map.Entry<String, String> pair : newParams.entrySet()) {
            jdbcTemplate.update(NodeQuery.UPDATE_PARAMS, pair.getKey(), pair.getValue(), nodeId, pair.getKey());
        }
        logger.info("extra params updated successfully ");

    }

    private void addHierarchy(Integer nodeId, Integer parentId, Integer rootId) {
        logger.info("Create Hierarchy  method. parent Id in database " + parentId
                + " root Id in database " + rootId + " child Id in database " + nodeId);
        jdbcTemplate.update(NodeQuery.ADD_HIERARCHY, nodeId, parentId, rootId);
        logger.info("Hierarchy created successfully ");
    }

    public void deleteNode(String nodeIdentifier) throws IdNotFoundException {
        logger.info("delete  node method. ");
        int nodeId = getIdDBById(nodeIdentifier);
        deleteHierarchy(nodeId);
        jdbcTemplate.update(NodeQuery.DELETE_NODE, nodeId);
        jdbcTemplate.update(NodeQuery.DELETE_PARAMS, nodeId);
        logger.info(" node deleted successfully. ");

    }

    private void deleteHierarchy(Integer nodeId) {
        logger.info("delete Hierarchy  method.  " + " Node Id in database " + nodeId);
        Integer parentId = jdbcTemplate.queryForObject(NodeQuery.SELECT_HIERARCHY_NODE_PARENT, Integer.class, nodeId);
        Integer rootId = jdbcTemplate.queryForObject(NodeQuery.SELECT_HIERARCHY_NODE_ROOT, Integer.class, nodeId);

        jdbcTemplate.update(NodeQuery.DELETE_HIERARCHY_NODE, nodeId);
        List<Node> children = jdbcTemplate.query(NodeQuery.SELECT_HIERARCHY_NODE_CHILDREN, new Object[]{nodeId}, new NodeMapper());
        for (Node child : children) {
            jdbcTemplate.update(NodeQuery.ADD_HIERARCHY, child.getIdDB(), parentId, rootId);
        }

        jdbcTemplate.update(NodeQuery.DELETE_HIERARCHY_CHILDREN, nodeId);
        logger.info("Hierarchy deleted successfully ");


    }

    public void updateChild(String parentNodeIdentifier, String childNodeIdentifier,
                            Node node) throws HierarchyException, IdNotFoundException {
        logger.info("update node  method. parent Id  " + parentNodeIdentifier
                + " child Id  " + childNodeIdentifier);
        Integer parentId = getIdDBById(parentNodeIdentifier);
        Integer childId = getIdDBById(childNodeIdentifier);
        Node parent = getNodeById(getIdDBById(parentNodeIdentifier));
        validatorManager.validate(node, parent);
        Object[] params = new Object[]{node.getType(), node.getName(), node.getDescription(), childNodeIdentifier, childId};
        if (Objects.requireNonNull(jdbcTemplate.queryForObject(NodeQuery.CHECK_HIERARCHY, Integer.class, parentId, childId)).equals(1)) {
            jdbcTemplate.update(NodeQuery.UPDATE_NODE, params);
            if (!node.getParams().isEmpty()) {
                updateParams(node.getParams(), childId);
            }
        }
        logger.info(" node  updated successfully ");
    }

    public void updateRootNode(Node node) {
        logger.info("update root node  method. ");
        Object[] params =
                new Object[]{node.getType(), node.getName(), node.getDescription(), node.getId()};
        jdbcTemplate.update(NodeQuery.UPDATE_ROOT, params);
        if (!node.getParams().isEmpty()) {
            updateParams(node.getParams(), node.getIdDB());
        }
        logger.info(" node  updated successfully ");

    }

    public Integer getIdDBById(String identifier) throws IdNotFoundException {
        logger.info(" get node's id in db by id .Id is " + identifier);
        List<Integer> integers =
                jdbcTemplate.queryForList(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, identifier);
        if (integers.isEmpty()) {
            throw new IdNotFoundException();
        }
        return jdbcTemplate.queryForList(NodeQuery.GET_ID_BY_IDENTIFIER, Integer.class, identifier).get(0);

    }

    public Node getNodeById(int id) {
        logger.info(" get node by id .Id is " + id);
        List<Node> node = jdbcTemplate.query(NodeQuery.SELECT_NODE_BY_ID, new Object[]{id}, new NodeMapper());
        if (node.isEmpty()) {
            return null;
        }
        return node.get(0);

    }

    void getParams(Node node) {
        logger.info("get extra params  method. Id in database " + node.getIdDB());
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
