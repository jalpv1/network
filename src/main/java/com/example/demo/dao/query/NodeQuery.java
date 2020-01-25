package com.example.demo.dao.query;

public class NodeQuery {
    public static  final String TABLE_NAME_NODE_INFO ="nodes_info";
    public static  final String NODE_ID ="node_id";
    public static  final String TYPE ="type";
    public static  final String NAME = "name";
    public static  final String DESCRIPTIOPN  ="description";

    public static  final String TABLE_NAME_PARAMS ="node_params";
    public static  final String PARAM_NAME ="param_name";
    public static  final String VALUE = "param_value";

    public static  final String TABLE_NAME_HIERARCHY ="hierarchy";
    public static  final String PARENT_ID ="parent_id";
    public static  final String ROOT_ID ="root_id";

    public static  final String CREATE_NODE  ="INSERT INTO "+ TABLE_NAME_NODE_INFO+ "( "+TYPE +","+ NAME +"," + DESCRIPTIOPN +" )"
            +" VALUES (?,?,?) ";// + " RETURNING "+NODE_ID;
    public static  final String ADD_PARAMS  ="INSERT INTO "+ TABLE_NAME_PARAMS+ "( "+NODE_ID +","+ PARAM_NAME +"," + VALUE +" )"
            +" VALUES (?,?,?)";
    public static  final String ADD_HIERARCHY  ="INSERT INTO "+ TABLE_NAME_HIERARCHY+ "( "+NODE_ID +","+ PARENT_ID +"," + ROOT_ID +" )"
            +" VALUES (?,?,?)";
    public static final String GET_NODES_ID = "SELECT MAX( "+NODE_ID +") FROM "+TABLE_NAME_NODE_INFO;

    public static final String DELETE_NODE = "DELETE  FROM "+TABLE_NAME_NODE_INFO +"" +
            " WHERE  "+NODE_ID + " =(?)";
    public static final String DELETE_PARAMS = "DELETE  FROM "+TABLE_NAME_PARAMS +"" +
            " WHERE  "+NODE_ID + " =(?)";
    public static final String SELECT_CHILD = "SELECT "+NODE_ID+"  FROM "+TABLE_NAME_HIERARCHY +
            " " +
            " WHERE  " + PARENT_ID + " =(?)" ;
    public static final String DELETE_HIERARCHY = "DELETE  FROM "+TABLE_NAME_HIERARCHY +"" +
            " WHERE  "+NODE_ID + " =(?)" + " OR " +PARENT_ID + " =(?)" + " OR "
            + ROOT_ID + " =(?)";
}
