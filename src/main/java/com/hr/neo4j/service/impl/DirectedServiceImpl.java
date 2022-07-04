package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.DirectedRepository;
import com.hr.neo4j.model.*;
import com.hr.neo4j.service.DirectedService;
import com.hr.neo4j.service.MovieService;
import com.hr.neo4j.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectedServiceImpl implements DirectedService {
    @Resource
    private DirectedRepository directedRepository;
    @Resource
    private PersonService personService;
    @Resource
    private MovieService movieService;



    @Override
    public Directed updateDirected(DirectedVo directedVo) {
        Long directedId = directedVo.getId();
        String label = directedVo.getLabel();
        //MATCH ()-[r:relation]->() where id(r)=33 set r.name="师生以" RETURN r
        return directedRepository.updateDirected(directedId,label);
    }

    /* *
     *
     * @param directedVoList
     * @return
     */
    @Override
    public Boolean addRelationList(List<DirectedVo> directedVoList) {
        for (DirectedVo directedVo : directedVoList) {
            this.addDirected(directedVo);
        }
        return Boolean.TRUE;
    }

    @Override
    public Map<String, Object> getSourceAndTargetAllRelationBySourceNodeId(Long sourceNodeId) {

        // 获取数据
        List<Person> personList = directedRepository.getSourceNodeBySourceNodeId(sourceNodeId);
        List<Movie> movieList = directedRepository.getTargetNodeBySourceNodeId(sourceNodeId);
        List<Directed> directedList = directedRepository.getSourceAndTargetAllRelationBySourceNodeId(sourceNodeId);

        // 返回数据
        return getIntegrationData(personList, movieList, directedList);
    }

    /* *
     *
     * @param nodeId
     * @return
     */
    @Override
    public Boolean delNodeAndRelationByNodeId(Long nodeId) {
        directedRepository.delNodeAndRelationByNodeId(nodeId);
        return Boolean.TRUE;
    }

    @Override
    public List<Directed> selectAllRelation() {
        return directedRepository.selectAllRelation();
    }

    /* *
     * 查询所有的person节点、电影节点、以及person和movie的关系
     * @return
     */
    @Override
    public Map<String, Object> selectAll() {
        // 获取数据
        List<Person> personList = personService.selectAllPerson();
        List<Movie> movieList = movieService.selectAllMovie();
        List<Directed> selectAllRelation = this.selectAllRelation();

        // 返回结果
        return getIntegrationData(personList, movieList, selectAllRelation);
    }

    // 组装数据
    private Map<String, Object> getIntegrationData(List<Person> personList, List<Movie> movieList, List<Directed> selectAllRelation) {
        if (!CollectionUtils.isEmpty(selectAllRelation)) {
            for (Directed directed : selectAllRelation) {
                directed.setSource(String.valueOf(directed.getStartNode().getId()));
                directed.setTarget(String.valueOf(directed.getEndNode().getId()));
            }
        }

        //封装结果
        Map<String, Object> map = new HashMap<>();
        map.put("personList", personList);
        map.put("movieList", movieList);
        map.put("selectAllRelation", selectAllRelation);
        return map;
    }


    /* *
     * 根据person的Id和电影Id删除关系
     * @param personId
     * @param movieId
     * @param type
     * @return
     */
    @Override
    public Boolean delDirectedById(Long relationId) {
//        directedRepository.delDirectedById(relationId);
        directedRepository.deleteById(relationId);
        return Boolean.TRUE;
    }

    /* *
     * 根据personName和movieTitle删除关系
     * @param personId
     * @param movieId
     * @return
     */
    @Override
    public Boolean delDirected(String personName, String movieTitle, Integer type) {
        if (type == 1) {  // person -> movie
            directedRepository.delDirected(personName, movieTitle);
        } else { // movie -> person
            directedRepository.delDirectedReverse(movieTitle, personName);
        }
        return Boolean.TRUE;
    }

    /* *
     * 删除所有person和电影的关系
     * @return
     */
    @Override
    public Boolean delAllDirected() {
        directedRepository.delAllDirected();
        return Boolean.TRUE;
    }

    /* *
     * 添加关系
     * @param personId
     * @param movieId
     * @return
     */
    @Override
    public Boolean addDirected(DirectedVo directedVo) {
        // 获取属性值
        Long personId = directedVo.getPersonId();
        Long movieId = directedVo.getMovieId();
        String label = directedVo.getLabel();
        Integer type = directedVo.getType();

        // 获取对应的节点信息
        Person person = personService.findOnePerson(personId);
        Movie movie = movieService.findOneMovie(movieId);

        //保存关系
        if (type == null || type == 1) {  // person --> movie
            Directed directed = new Directed();
            directed.setStartNode(person);
            directed.setEndNode(movie);
            directed.setLabel(label);
            directedRepository.save(directed);
        } else if (type == 2) { // movie  --> person
            directedRepository.saveRelationReverse(personId, movieId, label);
        } else if (type == 3) { // 创建双向连接
            // 建立双向连接
            String reverseLabel = directedVo.getReverseLable();
            directedRepository.saveRelation(personId, movieId, label, reverseLabel);
        }

        return Boolean.TRUE;
    }

}

