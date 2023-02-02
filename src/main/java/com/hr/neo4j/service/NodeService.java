package com.hr.neo4j.service;

import com.hr.neo4j.base.Node;

import java.util.List;

public interface NodeService {
    /**
     * 根据名称查找节点信息
     * @param name
     * @return
     */
    List<Node> findNodeInfoByName(String name);
}
