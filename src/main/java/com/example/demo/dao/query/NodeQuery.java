package com.example.demo.dao.query;

public class NodeQuery {
    public static final String TABLE_NAME_NODE_INFO = "nodes_info";
    public static final String NODE_ID = "node_id";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String DESCRIPTIOPN = "description";
    public static final String IDENTIFIER = "identifier";
    public static final String TABLE_NAME_PARAMS = "node_params";
    public static final String PARAM_NAME = "param_name";
    public static final String VALUE = "param_value";

    public static final String TABLE_NAME_HIERARCHY = "hierarchy";
    public static final String PARENT_ID = "parent_id";
    public static final String ROOT_ID = "root_id";

    public static final String CREATE_NODE = "INSERT INTO " + TABLE_NAME_NODE_INFO + "( " + TYPE + "," + NAME + "," + DESCRIPTIOPN + " )"
            + " VALUES (?,?,?) ";// + " RETURNING "+NODE_ID;
    public static final String ADD_PARAMS = "INSERT INTO " + TABLE_NAME_PARAMS + "( " + NODE_ID + "," + PARAM_NAME + "," + VALUE + " )"
            + " VALUES (?,?,?)";
    public static final String ADD_HIERARCHY = "INSERT INTO " + TABLE_NAME_HIERARCHY + "( " + NODE_ID + "," + PARENT_ID + "," + ROOT_ID + " )"
            + " VALUES (?,?,?)";
    public static final String GET_NODES_ID = "SELECT MAX( " + NODE_ID + ") FROM " + TABLE_NAME_NODE_INFO;

    public static final String DELETE_NODE = "DELETE  FROM " + TABLE_NAME_NODE_INFO + "" +
            " WHERE  " + NODE_ID + " =(?)";
    // public static final String UPDATE_NODE_IDENTIFIER = "UPDATE "+TABLE_NAME_NODE_INFO +" SET "+ IDENTIFIER + " = (?) "+ " WHERE  "+NODE_ID + " = (?)";

    public static final String UPDATE_NODE_IDENTIFIER = "UPDATE " + TABLE_NAME_NODE_INFO + " SET " +
            IDENTIFIER + "=(?) "
            + " WHERE " + NODE_ID + "=(?)";
    public static final String DELETE_PARAMS = "DELETE  FROM " + TABLE_NAME_PARAMS + "" +
            " WHERE  " + NODE_ID + " =(?)";
    public static final String SELECT_CHILD = "SELECT " + NODE_ID + "  FROM " + TABLE_NAME_HIERARCHY +
            " " +
            " WHERE  " + PARENT_ID + " =(?)";
    public static final String SELECT_NODE_BY_ID = "SELECT * " + "  FROM " + TABLE_NAME_NODE_INFO +
            " " +
            " WHERE  " + NODE_ID + " =(?)";
    public static final String SELECT_ROOT = "SELECT " + ROOT_ID + "  FROM " + TABLE_NAME_HIERARCHY +
            " " +
            " WHERE  " + PARENT_ID + " =(?)" + " LIMIT 1";
    public static final String DELETE_HIERARCHY = "DELETE  FROM " + TABLE_NAME_HIERARCHY + "" +
            " WHERE  " + NODE_ID + " =(?)" + " OR " + PARENT_ID + " =(?)" + " OR "
            + ROOT_ID + " =(?)";
    public static final String SELECT_HIERARCHY_NODE_PARENT = "SELECT " + PARENT_ID + " FROM " + TABLE_NAME_HIERARCHY + "" +
            " WHERE  " + NODE_ID + " =(?)";
    public static final String SELECT_HIERARCHY_NODE_ROOT = "SELECT " + ROOT_ID + " FROM " + TABLE_NAME_HIERARCHY + "" +
            " WHERE  " + NODE_ID + " =(?)";
    public static final String SELECT_HIERARCHY_NODE_CHILDREN = "SELECT  "
            + TABLE_NAME_NODE_INFO + "." + NODE_ID + " , " + TYPE + " , " + NAME + " , " + DESCRIPTIOPN + " , "
            + IDENTIFIER + " FROM " + TABLE_NAME_NODE_INFO + " INNER JOIN " + TABLE_NAME_HIERARCHY + " h" +
            " ON " + TABLE_NAME_NODE_INFO + "." + NODE_ID + " = " + "h." + NODE_ID + " WHERE " + PARENT_ID + " =(?)";
    //SELECT nodes_info.node_id,type,name,description,identifier FROM nodes_info INNER JOIN hierarchy h on nodes_info.node_id = h.node_id;
    public static final String DELETE_HIERARCHY_NODE = "DELETE  FROM " + TABLE_NAME_HIERARCHY + "" +
            " WHERE  " + NODE_ID + " =(?)";
    public static final String DELETE_HIERARCHY_CHILDREN = "DELETE  FROM " + TABLE_NAME_HIERARCHY + "" +
            " WHERE  " + PARENT_ID + " =(?)";
    public static final String UPDATE_NODE = "UPDATE " + TABLE_NAME_NODE_INFO + " SET " +
            TYPE + "=(?) " + " , " + NAME + "=(?) " + " , " + DESCRIPTIOPN + "=(?) " + " , " +
            IDENTIFIER + "=(?) "
            + " WHERE " + NODE_ID + "=(?)";
    public static final String UPDATE_ROOT = "UPDATE " + TABLE_NAME_NODE_INFO + " SET " +
            TYPE + "=(?) " + " , " + NAME + "=(?) " + " , " + DESCRIPTIOPN + "=(?) "
            + " WHERE " + IDENTIFIER + "=(?)";
    public static final String UPDATE_PARAMS = "UPDATE " + TABLE_NAME_PARAMS + " SET " +
            PARAM_NAME + "=(?) " + " , " + VALUE + "=(?) "
            + " WHERE " + NODE_ID + "=(?)" + " AND " + PARAM_NAME + "=(?)";
    public static final String GET_ID_BY_IDENTIFIER = "SELECT " + NODE_ID + "  FROM " + TABLE_NAME_NODE_INFO +
            " " +
            " WHERE  " + IDENTIFIER + " =(?)";
    public static final String CHECK_HIERARCHY = "SELECT count(*)" + " FROM " + TABLE_NAME_HIERARCHY + " WHERE " + PARENT_ID + "=(?) "
            + " AND " + NODE_ID + "=(?) ";

    public static final String SELECT_HIERARCHY_NETWORK_BY_ROOT = "SELECT  "
            + TABLE_NAME_NODE_INFO + "." + NODE_ID + " , " + TYPE + " , " + NAME + " , " + DESCRIPTIOPN + " , "
            + IDENTIFIER + " FROM " + TABLE_NAME_NODE_INFO + " INNER JOIN " + TABLE_NAME_HIERARCHY + " h" +
            " ON " + TABLE_NAME_NODE_INFO + "." + NODE_ID + " = " + "h." + NODE_ID + " WHERE " + ROOT_ID + " =(?)";

    public static final String SELECT_ROOTS = "SELECT DISTINCT "
            + TABLE_NAME_NODE_INFO + "." + NODE_ID + " , " + TYPE + " , " + NAME + " , " + DESCRIPTIOPN + " , "
            + IDENTIFIER + " FROM " + TABLE_NAME_NODE_INFO + " INNER JOIN " + TABLE_NAME_HIERARCHY + " h" +
            " ON " + TABLE_NAME_NODE_INFO + "." + NODE_ID + " = " + "h." + ROOT_ID;
    public static final String CONDITION_SEARCH_BY_NAME = " WHERE " + NAME + " ILIKE " + "(?)";
    public static final String CONDITION_SEARCH_BY_NAME_NODE = " AND " + NAME + " ILIKE " + "(?)";

    public static final String SELECT_HIERARCHY_NODES = "SELECT  "
            + TABLE_NAME_NODE_INFO + "." + NODE_ID + " , " + TYPE + " , " + NAME + " , " + DESCRIPTIOPN + " , "
            + IDENTIFIER + " FROM " + TABLE_NAME_NODE_INFO + " INNER JOIN " + TABLE_NAME_HIERARCHY + " h" +
            " ON " + TABLE_NAME_NODE_INFO + "." + NODE_ID + " = " + "h." + NODE_ID;
    public static final String GET_PARAMS = "SELECT " +PARAM_NAME + " , "+VALUE + " FROM "+ TABLE_NAME_PARAMS
    +  " WHERE " + NODE_ID + " =(?) ";
}
