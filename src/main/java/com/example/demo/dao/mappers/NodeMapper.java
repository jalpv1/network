package com.example.demo.dao.mappers;

import com.example.demo.dao.query.NodeQuery;
import com.example.demo.entity.Node;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NodeMapper  implements RowMapper<Node> {
    public Node mapRow(ResultSet resultSet,int i) throws SQLException {
        Node node = new Node();
        node.setIdDB(resultSet.getInt(NodeQuery.NODE_ID));
        node.setType(resultSet.getString(NodeQuery.TYPE));
        node.setName(resultSet.getString(NodeQuery.NAME));
        node.setDescription(resultSet.getString(NodeQuery.DESCRIPTIOPN));
        node.setIdentifier(resultSet.getString(NodeQuery.IDENTIFIER));

        return node;
    }
}
