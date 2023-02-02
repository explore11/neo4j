package com.hr.neo4j.service.impl;

import com.hr.neo4j.base.Node;
import com.hr.neo4j.dao.NodeRepository;
import com.hr.neo4j.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class NodeServiceImpl implements NodeService {

    @Resource
    private NodeRepository nodeRepository;

    /**
     * 根据名称查找节点信息
     *
     * @param name
     * @return
     */
    @Override
    public List<Node> findNodeInfoByName(String name) {
        List<Node> nodeList = nodeRepository.findNodeInfoByName(name);
        return nodeList;
    }
}
