package com.example.demo.dao.mappers;

import com.example.demo.dao.query.NodeQuery;
import com.example.demo.entity.Node;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NodeMapper {
    public Node mapRow(ResultSet resultSet) throws SQLException {
        Node node = new Node();
        node.setId(resultSet.getInt(NodeQuery.NODE_ID));
        node.setType(resultSet.getString(NodeQuery.TYPE));
        node.setName(resultSet.getString(NodeQuery.NAME));
        node.setDescription(resultSet.getString(NodeQuery.DESCRIPTIOPN));


        return node;
    }
}
