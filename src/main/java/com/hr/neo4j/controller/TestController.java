package com.hr.neo4j.controller;

import com.hr.neo4j.service.TestService;
import com.hr.neo4j.util.Neo4jUtil;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/test")
@Api(tags = "添加测试数据")
public class TestController {

    @Autowired
    private Neo4jUtil neo4jUtil;

    @Resource
    private TestService testService;

    @GetMapping("/addTest")
    private Result addTest(Integer num) {
        testService.addTestData(num);

        return Result.success(Boolean.TRUE);
    }

    @GetMapping("get")
    public Map<String, Object> get() {
        Map<String, Object> retMap = new HashMap<>();
        //cql语句
//        String cql = "match (m:Person{name: '张三'}) return m";
        String cql = "match p=(n)-[*1..1]->(m) where ID(n)=" + 15509 + " return p,n,m";
        Set<Map<String, Object>> nodeList = new HashSet<>();
        neo4jUtil.getList(cql, nodeList);
        retMap.put("nodeList", nodeList);
        return retMap;
    }

    @GetMapping("getList")
    public Map<String, Object> getList(String id) {
        Map<String, Object> retMap = new HashMap<>();
        //cql语句
        String cql = "match edge=(n)-[*1..1]->(m) where ID(n)=" + id + " return edge,n,m";
        Set<Map<String, Object>> pNodeList = new HashSet<>();
        Set<Map<String, Object>> nNodeList = new HashSet<>();
        Set<Map<String, Object>> mNodeList = new HashSet<>();
        neo4jUtil.getList(cql, pNodeList, nNodeList, mNodeList);
        retMap.put("pNodeList", pNodeList);
        retMap.put("nNodeList", nNodeList);
        retMap.put("mNodeList", mNodeList);
        return retMap;
    }


    @GetMapping("getPath")
    public Map<String, Object> getPath(String id) {
        Map<String, Object> retMap = new HashMap<>();
        //cql语句  ID()可以获取节点自动生成的id
        String cql = "match p=(m)-[]-(n) where ID(m)=" + id + " return p";
        //待返回的值，与cql return后的值顺序对应
        Set<Map<String, Object>> nodeList = new HashSet<>();
        Set<Map<String, Object>> edgeList = new HashSet<>();
        neo4jUtil.getPathList(cql, nodeList, edgeList);
        retMap.put("nodeList", nodeList);
        retMap.put("edgeList", edgeList);
        return retMap;
    }

    @GetMapping("getShortPath")
    public Map<String, Object> getShortPath() {
        Map<String, Object> retMap = new HashMap<>();
        //cql语句
        String cql = "match l=shortestPath(({name:'Keanu Reeves'})-[*]-({title:\"Jerry Maguire\"})) return l";
        //待返回的值，与cql return后的值顺序对应
        Set<Map<String, Object>> nodeList = new HashSet<>();
        Set<Map<String, Object>> edgeList = new HashSet<>();
        neo4jUtil.getPathList(cql, nodeList, edgeList);
        retMap.put("nodeList", nodeList);
        retMap.put("edgeList", edgeList);
        return retMap;
    }

    @GetMapping("getFields")
    public Map<String, Object> getFields() {
        Map<String, Object> retMap = new HashMap<>();
        //cql语句
        //String cql = "match (n:Person{name:\"Anthony Edwards\"}) return n.name as name,n.born as born";
        String cql = "match (n:Person) return count(n) as cou";
        retMap.put("fieldList", neo4jUtil.getFields(cql));
        return retMap;
    }

    @GetMapping("add")
    public Result add() {
        //创建单个节点
        //String cql = "create (:Person{name:\"康康\"})";
        //创建多个节点
        //String cql = "create (:Person{name:\"李雷\"}) create (:Person{name:\"小明\"})";
        //根据已有节点创建关系
        //String cql = "match (n:Person{name:\"李雷\"}),(m:Person{name:\"小明\"}) create (n)-[r:friendRelation]->(m)";
        //同时创建节点和关系
//        String cql = "create (p:Person{name:'张三'})-[r:friendRelation]->(p2:Person{name:'王五'}) return p,r,p2";
        String cql = "create (p:Person{name:'张三'}) return p";
        Map<String, Object> map = neo4jUtil.add(cql);
        return Result.success(map);
    }

}
