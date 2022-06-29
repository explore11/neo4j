package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.DirectedRepository;
//import com.hr.neo4j.dao.DirectedReverseRepository;
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
//    @Resource
//    private DirectedReverseRepository directedReverseRepository;


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
    public List<Directed> getSourceAndTargetAllRelationBySourceNodeId(Long sourceNodeId) {
        List<Directed> list = directedRepository.getSourceAndTargetAllRelationBySourceNodeId(sourceNodeId);
        System.out.println(list);
        return list;
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
        // 组装数据
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
        directedRepository.delDirectedById(relationId);
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

        // 获取对应的节点信息
        Long personId = directedVo.getPersonId();
        Long movieId = directedVo.getMovieId();
        Integer type = directedVo.getType();
        String lable = directedVo.getLable();

        Person person = personService.findOnePerson(personId);
        Movie movie = movieService.findOneMovie(movieId);

        // 判断添加关系类型
        // type = 1     person -> movie
        // type = 2     movie -> person
//        if (type != null && type == 2) {  //movie -> person
//            DirectedNodeReverse directedNodeReverse = new DirectedNodeReverse();
//            directedNodeReverse.setStartNode(movie);
//            directedNodeReverse.setEndNode(person);
//            directedNodeReverse.setLable(lable);
//            directedReverseRepository.save(directedNodeReverse);
//        } else {  // 默认是  person -> movie
            Directed directed = new Directed();
            directed.setStartNode(person);
            directed.setEndNode(movie);
            directed.setLable(lable);
            directedRepository.save(directed);
//        }
        return Boolean.TRUE;
    }

}

