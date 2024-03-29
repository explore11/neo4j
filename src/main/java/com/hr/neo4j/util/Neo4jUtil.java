package com.hr.neo4j.util;

import com.hr.neo4j.base.NodeValue;
import com.hr.neo4j.base.RelationshipValue;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.neo4j.driver.v1.types.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/* *
 * 通用的neo4j调用类
 */
@Component
public class Neo4jUtil {
    private static Driver driver;

    @Autowired
    public Neo4jUtil(Driver driver) {
        Neo4jUtil.driver = driver;
    }

    /* *
     * cql的return返回多种节点match (n)-[edge]-(n) return n,m,edge：限定返回关系时，关系的别名必须“包含”edge
     * @param cql   查询语句
     * @param lists 和cql的return返回节点顺序对应
     * @return List<Map < String, Object>>
     */
    public static <T> void getList(String cql, Set<T>... lists) {
        //用于给每个Set list赋值
        int listIndex = 0;
        try {
            Session session = driver.session();
            StatementResult result = session.run(cql);
            List<Record> list = result.list();
            for (Record r : list) {
                if (r.size() != lists.length) {
                    System.out.println("节点数和lists数不匹配");
                    return;
                }
            }
            for (Record r : list) {
                for (String index : r.keys()) {
                    //对于关系的封装
                    if (index.indexOf("edge") != -1) {
                        //关系上设置的属性
                        Value value = r.get(index);
                        Path path = value.asPath();
                        Iterable<Relationship> edges = path.relationships();
                        for (Iterator iter = edges.iterator(); iter.hasNext(); ) {
                            InternalRelationship relationInter = (InternalRelationship) iter.next();
                            Map<String, Object> edgeMap = new HashMap<>();
                            edgeMap.putAll(relationInter.asMap());
                            //关系上设置的属性
                            edgeMap.put("edgeId", relationInter.id());
                            edgeMap.put("edgeFrom", relationInter.startNodeId());
                            edgeMap.put("edgeTo", relationInter.endNodeId());
                            lists[listIndex++].add((T) edgeMap);
                        }
                    }
                    //对于节点的封装
                    else {
                        Map<String, Object> map = new HashMap<>();
                        //关系上设置的属性
                        map.putAll(r.get(index).asMap());
                        //外加一个固定属性
                        map.put("nodeId", r.get(index).asNode().id());
                        lists[listIndex++].add((T) map);
                    }
                }
                listIndex = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* *
     * cql 路径查询 返回节点和关系
     * @param cql      查询语句
     * @param nodeList 节点
     * @param edgeList 关系
     * @return List<Map < String, Object>>
     */
    public static <T> void getPathList(String cql, Set<T> nodeList, Set<T> edgeList) {
        try {
            Session session = driver.session();
            StatementResult result = session.run(cql);
            List<Record> list = result.list();
            for (Record r : list) {
                for (String index : r.keys()) {
                    Path path = r.get(index).asPath();
                    //节点
                    Iterable<Node> nodes = path.nodes();
                    for (Iterator iter = nodes.iterator(); iter.hasNext(); ) {
                        InternalNode nodeInter = (InternalNode) iter.next();
                        Map<String, Object> map = new HashMap<>();
                        //节点上设置的属性
                        map.putAll(nodeInter.asMap());
                        //外加一个固定属性
                        map.put("nodeId", nodeInter.id());
                        nodeList.add((T) map);
                    }
                    //关系
                    Iterable<Relationship> edges = path.relationships();
                    for (Iterator iter = edges.iterator(); iter.hasNext(); ) {
                        InternalRelationship relationInter = (InternalRelationship) iter.next();
                        Map<String, Object> map = new HashMap<>();
                        map.putAll(relationInter.asMap());
                        //关系上设置的属性
                        map.put("edgeId", relationInter.id());
                        map.put("edgeFrom", relationInter.startNodeId());
                        map.put("edgeTo", relationInter.endNodeId());
                        edgeList.add((T) map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* *
     * cql 返回具体的属性, 如match (n)-[]-() return n.id,n.name，match (n)-[]-() return count(n)
     * @param cql 查询语句
     * @return List<Map < String, Object>>
     */
    public static List<Map<String, Object>> getFields(String cql) {
        List<Map<String, Object>> resList = new ArrayList<>();
        try {
            Session session = driver.session();
            StatementResult result = session.run(cql);
            List<Record> list = result.list();
            for (Record r : list) {
                resList.add(r.asMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resList;
    }

    /* *
     * 执行添加cql  返回添加结果  map的key 是 cql中 return 后面的 参数名
     * @param cql 查询语句
     */
    public static Map<String, Object> add(String cql) {
        Map<String, Object> map = new HashMap<>();
        //启动事务
        try (Session session = driver.session();
             Transaction tx = session.beginTransaction()) {
            StatementResult result = tx.run(cql);
            parseResult(map, result);
            //提交事务
            tx.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void parseResult(Map<String, Object> map, StatementResult result) {
        List<Record> list = result.list();
        for (Record record : list) {
            List<String> keys = record.keys();
            List<Value> values = record.values();

            if (!CollectionUtils.isEmpty(keys)) {
                for (int i = 0; i < keys.size(); i++) {
                    // 拿到key
                    String key = keys.get(i);
                    //拿到对应的value
                    Value value = values.get(i);
                    // 获取类型名称
                    Type type = value.type();
                    String name = type.name();
                    // 不同类型 区别处理
                    if (Neo4jResultType.NODE.equals(name)) {
                        Node node = value.asNode();
                        NodeValue nodeValue = new NodeValue();
                        nodeValue.setId(node.id());
                        nodeValue.setProperties(node.asMap());
                        nodeValue.setLabels(node.labels());
                        map.put(key, nodeValue);
                    } else {
                        Relationship relationship = value.asRelationship();
                        RelationshipValue relationshipValue = new RelationshipValue();
                        relationshipValue.setId(relationship.id());
                        relationshipValue.setStart(relationship.startNodeId());
                        relationshipValue.setEnd(relationship.endNodeId());
                        relationshipValue.setType(relationship.type());
                        relationshipValue.setProperties(relationship.asMap());
                        map.put(key, relationshipValue);
                    }
                }
            }
        }
    }
}
