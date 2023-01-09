package com.hr.neo4j.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.hr.neo4j.base.FileData;
import com.hr.neo4j.base.Node;
import com.hr.neo4j.base.Relationship;
import com.hr.neo4j.dao.NodeRepository;
import com.hr.neo4j.dao.RelationshipRepository;
import com.hr.neo4j.exception.MicroServiceException;
import com.hr.neo4j.listener.EasyExcelListener;
import com.hr.neo4j.service.ReaderDataService;
import com.hr.neo4j.util.IdUtils;
import com.hr.neo4j.util.RelationshipConstant;
import com.hr.neo4j.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReaderDataServiceImpl implements ReaderDataService {

    @Resource
    private NodeRepository nodeRepository;

    @Resource
    private RelationshipRepository relationshipRepository;

    /**
     * 导入数据
     *
     * @param file
     */
    @Override
    public void ImportData(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), FileData.class, new EasyExcelListener()).sheet().headRowNumber(1).doRead();
    }


    /**
     * 解析数据
     *
     * @param fileDataList
     */
    @Override
    public void parseData(List<FileData> fileDataList) {
        //定义集合
        List<FileData> clearAfterDataList = new ArrayList<>();

        //清晰数据 去除全部为空的对象
        for (FileData fileData : fileDataList) {
            if (!checkObjAllFieldsIsNull(fileData)) {
                clearAfterDataList.add(fileData);
            }
        }

        //数据校验
        if (CollectionUtils.isEmpty(clearAfterDataList)) {
            throw new MicroServiceException(ResultCode.DATA_IS_WRONG.code(), ResultCode.DATA_IS_WRONG.message());
        }


        //获取事件编码
        //事件级别
        //电厂机组
        //堆型
        //直接原因
        //根本原因
        //涉及的系统
        //涉及的设备
        //受影响的设备
        //事件探测和保护
        //事件后果
        //处置措施

        //获取事件编码
        Set<String> eventCodeSet = new HashSet<>();
        List<Node> eventCodeNodeList = new ArrayList<>();

        //事件级别
        Set<String> eventLevelSet = new HashSet<>();
        List<Node> eventLevelNodeList = new ArrayList<>();

        //电厂机组
        Set<String> powerPlantUnitSet = new HashSet<>();
        List<Node> powerPlantUnitNodeList = new ArrayList<>();

        //堆型
        Set<String> stackTypeSet = new HashSet<>();
        List<Node> stackTypeNodeList = new ArrayList<>();

        //直接原因
        Set<String> directCauseSet = new HashSet<>();
        List<Node> directCauseNodeList = new ArrayList<>();

        //根本原因
        Set<String> rootCauseSet = new HashSet<>();
        List<Node> rootCauseNodeList = new ArrayList<>();

        //涉及的系统
        Set<String> involveSystemSet = new HashSet<>();
        List<Node> involveSystemNodeList = new ArrayList<>();

        //涉及的设备
        Set<String> involveFacilitySet = new HashSet<>();
        List<Node> involveFacilityNodeList = new ArrayList<>();

        //受影响的设备
        Set<String> affectFacilitySet = new HashSet<>();
        List<Node> affectFacilityNodeList = new ArrayList<>();

        //事件探测和保护
        Set<String> eventDetectionAndProtectionSet = new HashSet<>();
        List<Node> eventDetectionAndProtectionNodeList = new ArrayList<>();

        //事件后果
        Set<String> eventConsequenceSet = new HashSet<>();
        List<Node> eventConsequenceNodeList = new ArrayList<>();

        //处置措施
        Set<String> processMeasureSet = new HashSet<>();
        List<Node> processMeasureNodeList = new ArrayList<>();


        for (FileData fileData : clearAfterDataList) {
            // 添加事件编码的值
            String eventCode = fileData.getEventCode();
            if (StringUtils.isNotBlank(eventCode)) {
                eventCodeSet.add(eventCode);
            }
            //添加事件级别的值
            String eventLevel = fileData.getEventLevel();
            if (StringUtils.isNotBlank(eventLevel)) {
                eventLevelSet.add(eventLevel);
            }

            //添加电厂机组的值
            String powerPlantUnit = fileData.getPowerPlantUnit();
            if (StringUtils.isNotBlank(powerPlantUnit)) {
                powerPlantUnitSet.add(powerPlantUnit);
            }
            //添加堆型的值
            String stackType = fileData.getStackType();
            if (StringUtils.isNotBlank(stackType)) {
                stackTypeSet.add(stackType);
            }
            //添加直接原因的值
            String directCause = fileData.getDirectCause();
            if (StringUtils.isNotBlank(directCause)) {
                directCauseSet.add(directCause);
            }
            //添加根本原因的值
            String rootCause = fileData.getRootCause();
            if (StringUtils.isNotBlank(rootCause)) {
                rootCauseSet.add(rootCause);
            }
            //添加涉及的系统的值
            String involveSystem = fileData.getInvolveSystem();
            if (StringUtils.isNotBlank(involveSystem)) {
                involveSystemSet.add(involveSystem);
            }
            //添加涉及的设备的值
            String involveFacilityStr = fileData.getInvolveFacility();
            if (StringUtils.isNotBlank(involveFacilityStr)) {
                //设备分割
                String[] involveFacilitySplitArray = involveFacilityStr.split("、");
                involveFacilitySet.addAll(Arrays.asList(involveFacilitySplitArray));
            }
            //添加受影响的设备的值
            String affectFacility = fileData.getAffectFacility();
            if (StringUtils.isNotBlank(affectFacility)) {
                affectFacilitySet.add(affectFacility);
            }
            //添加事件探测和保护的值
            String eventDetectionAndProtection = fileData.getEventDetectionAndProtection();
            if (StringUtils.isNotBlank(eventDetectionAndProtection)) {
                eventDetectionAndProtectionSet.add(eventDetectionAndProtection);
            }
            //添加事件后果的值
            String eventConsequence = fileData.getEventConsequence();
            if (StringUtils.isNotBlank(eventConsequence)) {
                eventConsequenceSet.add(eventConsequence);
            }
            //添加处置措施的值
            String processMeasure = fileData.getProcessMeasure();
            if (StringUtils.isNotBlank(processMeasure)) {
                processMeasureSet.add(processMeasure);
            }

        }
        /**
         * 创建Node节点
         */
        for (String eventCode : eventCodeSet) {
            // 添加事件编码的随机值
            eventCodeNodeList.add(new Node(null, eventCode, IdUtils.simpleUUID()));
        }

        //添加事件级别的值
        for (String eventLevel : eventLevelSet) {
            eventLevelNodeList.add(new Node(null, eventLevel, IdUtils.simpleUUID()));
        }

        //添加电厂机组的值
        for (String powerPlantUnit : powerPlantUnitSet) {
            powerPlantUnitNodeList.add(new Node(null, powerPlantUnit, IdUtils.simpleUUID()));
        }

        //添加堆型的值
        for (String stackType : stackTypeSet) {
            stackTypeNodeList.add(new Node(null, stackType, IdUtils.simpleUUID()));
        }
        //添加直接原因的值
        for (String directCause : directCauseSet) {
            directCauseNodeList.add(new Node(null, directCause, IdUtils.simpleUUID()));
        }
        //添加根本原因的值
        for (String rootCause : rootCauseSet) {
            rootCauseNodeList.add(new Node(null, rootCause, IdUtils.simpleUUID()));
        }
        //添加涉及的系统的值
        for (String involveSystem : involveSystemSet) {
            involveSystemNodeList.add(new Node(null, involveSystem, IdUtils.simpleUUID()));
        }
        //添加涉及的设备的值
        for (String involveFacility : involveFacilitySet) {
            involveFacilityNodeList.add(new Node(null, involveFacility, IdUtils.simpleUUID()));
        }
        //添加受影响的设备的值
        for (String affectFacility : affectFacilitySet) {
            affectFacilityNodeList.add(new Node(null, affectFacility, IdUtils.simpleUUID()));
        }
        //添加事件探测和保护的值
        for (String eventDetectionAndProtection : eventDetectionAndProtectionSet) {
            eventDetectionAndProtectionNodeList.add(new Node(null, eventDetectionAndProtection, IdUtils.simpleUUID()));
        }
        //添加事件后果的值
        for (String eventConsequence : eventConsequenceSet) {
            eventConsequenceNodeList.add(new Node(null, eventConsequence, IdUtils.simpleUUID()));
        }
        //添加处置措施的值
        for (String processMeasure : processMeasureSet) {
            processMeasureNodeList.add(new Node(null, processMeasure, IdUtils.simpleUUID()));
        }

        //建立映射
        //获取事件编码
        Map<String, Node> eventCodeMap = eventCodeNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //事件级别
        Map<String, Node> eventLevelMap = eventLevelNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //电厂机组
        Map<String, Node> powerPlantUnitMap = powerPlantUnitNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //堆型
        Map<String, Node> stackTypeMap = stackTypeNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //直接原因
        Map<String, Node> directCauseMap = directCauseNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //根本原因
        Map<String, Node> rootCauseMap = rootCauseNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //涉及的系统
        Map<String, Node> involveSystemMap = involveSystemNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //涉及的设备
        Map<String, Node> involveFacilityMap = involveFacilityNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //受影响的设备
        Map<String, Node> affectFacilityMap = affectFacilityNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //事件探测和保护
        Map<String, Node> eventDetectionAndProtectionMap = eventDetectionAndProtectionNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //事件后果
        Map<String, Node> eventConsequenceMap = eventConsequenceNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //处置措施
        Map<String, Node> processMeasureMap = processMeasureNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));


        /**
         * 创建节点
         */

        //创建获取事件编码节点
        if (!CollectionUtils.isEmpty(eventCodeNodeList)) {
            nodeRepository.saveAll(eventCodeNodeList);
        }

        //创建事件级别节点
        if (!CollectionUtils.isEmpty(eventLevelNodeList)) {
            nodeRepository.saveAll(eventLevelNodeList);
        }
        //创建电厂机组节点
        if (!CollectionUtils.isEmpty(powerPlantUnitNodeList)) {
            nodeRepository.saveAll(powerPlantUnitNodeList);
        }
        //创建堆型节点
        if (!CollectionUtils.isEmpty(stackTypeNodeList)) {
            nodeRepository.saveAll(stackTypeNodeList);
        }
        //创建直接原因节点
        if (!CollectionUtils.isEmpty(directCauseNodeList)) {
            nodeRepository.saveAll(directCauseNodeList);
        }
        //创建根本原因节点
        if (!CollectionUtils.isEmpty(rootCauseNodeList)) {
            nodeRepository.saveAll(rootCauseNodeList);
        }
        //创建涉及的系统节点
        if (!CollectionUtils.isEmpty(involveSystemNodeList)) {
            nodeRepository.saveAll(involveSystemNodeList);
        }
        //创建涉及的设备节点
        if (!CollectionUtils.isEmpty(involveFacilityNodeList)) {
            nodeRepository.saveAll(involveFacilityNodeList);
        }
//        //创建受影响的设备节点
//        if (!CollectionUtils.isEmpty(affectFacilityNodeList)) {
//            nodeRepository.saveAll(affectFacilityNodeList);
//        }
        //创建事件探测和保护节点
        if (!CollectionUtils.isEmpty(eventDetectionAndProtectionNodeList)) {
            nodeRepository.saveAll(eventDetectionAndProtectionNodeList);
        }
        //创建事件后果节点
        if (!CollectionUtils.isEmpty(eventConsequenceNodeList)) {
            nodeRepository.saveAll(eventConsequenceNodeList);
        }
        //创建处置措施节点
        if (!CollectionUtils.isEmpty(processMeasureNodeList)) {
            nodeRepository.saveAll(processMeasureNodeList);
        }


        /**
         *  创建关系
         *
         *  事件编码   ==========>  事件级别            级别
         *  事件编码   ==========>  电厂机组            发生
         *  事件编码   ==========>  堆型               事件堆型
         *  事件编码   ==========>  直接原因            直接原因
         *  事件编码   ==========>  根本原因            根本原因
         *  事件编码   ==========>  涉及的系统          事件系统
         *  事件编码   ==========>  涉及的设备          事件设备
         *  事件编码   ==========>  受影响的设备        受影响的设备
         *  事件编码   ==========>  事件探测和保护       探测保护
         *  事件编码   ==========>  事件后果            后果
         *  事件编码   ==========>  处置措施            处置措施
         *
         *
         *  电厂机组   ==========>  堆型                属于
         *  堆型      ==========>  系统                 包含
         *  系统      ==========>  设备                 包含
         *  设备      ==========>  事件后果             设备的可能后果
         *  设备      ==========>  受影响的设备          可能影响的设备
         *
         *
         */
        List<Relationship> eventCodeToEventLevelRelationshipList = new ArrayList<>();

        //创建关系
        for (FileData fileData : clearAfterDataList) {
            // 添加事件编码的值
            String eventCode = fileData.getEventCode();
            //添加事件级别的值
            String eventLevel = fileData.getEventLevel();
            //添加电厂机组的值
            String powerPlantUnit = fileData.getPowerPlantUnit();
            //添加堆型的值
            String stackType = fileData.getStackType();
            //添加直接原因的值
            String directCause = fileData.getDirectCause();
            //添加根本原因的值
            String rootCause = fileData.getRootCause();
            //添加涉及的系统的值
            String involveSystem = fileData.getInvolveSystem();
            //添加涉及的设备的值
            String involveFacilityStr = fileData.getInvolveFacility();
            //添加受影响的设备的值
            String affectFacility = fileData.getAffectFacility();
            //添加事件探测和保护的值
            String eventDetectionAndProtection = fileData.getEventDetectionAndProtection();
            //添加事件后果的值
            String eventConsequence = fileData.getEventConsequence();
            //添加处置措施的值
            String processMeasure = fileData.getProcessMeasure();


            /**
             *  事件编码   ==========>  事件级别            级别
             *  事件编码   ==========>  电厂机组            发生
             *  事件编码   ==========>  堆型               事件堆型
             *  事件编码   ==========>  直接原因            直接原因
             *  事件编码   ==========>  根本原因            根本原因
             *  事件编码   ==========>  涉及的系统          事件系统
             *  事件编码   ==========>  涉及的设备          事件设备
             *  事件编码   ==========>  受影响的设备        受影响的设备
             *  事件编码   ==========>  事件探测和保护       探测保护
             *  事件编码   ==========>  事件后果            后果
             *  事件编码   ==========>  处置措施            处置措施
             */
            if (StringUtils.isNotBlank(eventCode)) {
                //获取事件编码对应的Node对象
                Node eventCodeStartNode = eventCodeMap.get(eventCode);
                // 事件编码   ==========>  事件级别            级别
                if (StringUtils.isNotBlank(rootCause)) {
                    // 构建 事件编码   ==========>  事件级别 对象
                    Relationship rootCauseRelationship = new Relationship();
                    rootCauseRelationship.setActualId(IdUtils.simpleUUID());
                    rootCauseRelationship.setName(rootCause);
                    Node eventLevelEndNode = rootCauseMap.get(rootCause);
                    rootCauseRelationship.setStartNode(eventLevelEndNode);

                    rootCauseRelationship.setEndNode(eventCodeStartNode);
                    eventCodeToEventLevelRelationshipList.add(rootCauseRelationship);
                }
            }


//            if (StringUtils.isNotBlank(powerPlantUnit)) {
//                powerPlantUnitSet.add(powerPlantUnit);
//            }
//
//            if (StringUtils.isNotBlank(stackType)) {
//                stackTypeSet.add(stackType);
//            }
//
//            if (StringUtils.isNotBlank(directCause)) {
//                directCauseSet.add(directCause);
//            }
//
//            if (StringUtils.isNotBlank(rootCause)) {
//                rootCauseSet.add(rootCause);
//            }
//
//            if (StringUtils.isNotBlank(involveSystem)) {
//                involveSystemSet.add(involveSystem);
//            }
//
//            if (StringUtils.isNotBlank(involveFacilityStr)) {
//                //设备分割
//                String[] involveFacilitySplitArray = involveFacilityStr.split("、");
//                involveFacilitySet.addAll(Arrays.asList(involveFacilitySplitArray));
//            }
//
//            if (StringUtils.isNotBlank(affectFacility)) {
//                affectFacilitySet.add(affectFacility);
//            }
//
//            if (StringUtils.isNotBlank(eventDetectionAndProtection)) {
//                eventDetectionAndProtectionSet.add(eventDetectionAndProtection);
//            }
//
//            if (StringUtils.isNotBlank(eventConsequence)) {
//                eventConsequenceSet.add(eventConsequence);
//            }
//
//            if (StringUtils.isNotBlank(processMeasure)) {
//                processMeasureSet.add(processMeasure);
//            }

        }

//        System.out.println(eventCodeToEventLevelRelationshipList);
        relationshipRepository.saveAll(eventCodeToEventLevelRelationshipList);

        System.out.println(clearAfterDataList);
    }


    /**
     * 判断属性中的所有内容为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        // 如果对象为null直接返回true
        if (null == object) {
            return true;
        }
        try {
            // 挨个获取对象属性值
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 如果有一个属性值不为null，且值不是空字符串，就返回false
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
