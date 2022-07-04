package com.hr.neo4j.service;

import com.hr.neo4j.model.Directed;
import com.hr.neo4j.model.DirectedVo;
import com.hr.neo4j.model.Movie;
import com.hr.neo4j.model.Person;

import java.util.List;
import java.util.Map;

public interface DirectedService {


    Boolean addDirected(DirectedVo directedVo);

    Boolean delDirected(String personName, String movieTitle, Integer type);

    Boolean delAllDirected();

    Map<String, Object> selectAll();

    List<Directed> selectAllRelation();

    Boolean delDirectedById(Long relationId);

    Boolean delNodeAndRelationByNodeId(Long nodeId);

    Map<String, Object> getSourceAndTargetAllRelationBySourceNodeId(Long sourceNodeId);

    Boolean addRelationList(List<DirectedVo> directedVoList);

    Directed updateDirected(DirectedVo directedVo);
}