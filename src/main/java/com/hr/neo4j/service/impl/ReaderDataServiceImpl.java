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
import com.hr.neo4j.util.NodeConstant;
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

//        //受影响的设备
//        Set<String> affectFacilitySet = new HashSet<>();
//        List<Node> affectFacilityNodeList = new ArrayList<>();

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
                if (affectFacility.contains("、") && !affectFacility.equals("反应堆厂房加热、通风和空调系统")) {
                    String[] affectFacilityArray = affectFacility.split("、");
                    involveFacilitySet.addAll(Arrays.asList(affectFacilityArray));
                } else {
                    involveSystemSet.add(affectFacility);
                }
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
            eventCodeNodeList.add(new Node(null, eventCode, IdUtils.simpleUUID(), NodeConstant.eventCodeType));
        }

        //添加事件级别的值
        for (String eventLevel : eventLevelSet) {
            eventLevelNodeList.add(new Node(null, eventLevel, IdUtils.simpleUUID(), NodeConstant.eventLevelType));
        }

        //添加电厂机组的值
        for (String powerPlantUnit : powerPlantUnitSet) {
            powerPlantUnitNodeList.add(new Node(null, powerPlantUnit, IdUtils.simpleUUID(), NodeConstant.powerPlantUnitType));
        }

        //添加堆型的值
        for (String stackType : stackTypeSet) {
            stackTypeNodeList.add(new Node(null, stackType, IdUtils.simpleUUID(), NodeConstant.stackTypeType));
        }
        //添加直接原因的值
        for (String directCause : directCauseSet) {
            directCauseNodeList.add(new Node(null, directCause, IdUtils.simpleUUID(), NodeConstant.directCauseType));
        }
        //添加根本原因的值
        for (String rootCause : rootCauseSet) {
            rootCauseNodeList.add(new Node(null, rootCause, IdUtils.simpleUUID(), NodeConstant.rootCauseType));
        }
        //添加涉及的系统的值
        for (String involveSystem : involveSystemSet) {
            involveSystemNodeList.add(new Node(null, involveSystem, IdUtils.simpleUUID(), NodeConstant.involveSystemType));
        }
        //添加涉及的设备的值
        for (String involveFacility : involveFacilitySet) {
            involveFacilityNodeList.add(new Node(null, involveFacility, IdUtils.simpleUUID(), NodeConstant.involveFacilityType));
        }
//        //添加受影响的设备的值
//        for (String affectFacility : affectFacilitySet) {
//            affectFacilityNodeList.add(new Node(null, affectFacility, IdUtils.simpleUUID()));
//        }

        //添加事件探测和保护的值
        for (String eventDetectionAndProtection : eventDetectionAndProtectionSet) {
            eventDetectionAndProtectionNodeList.add(new Node(null, eventDetectionAndProtection, IdUtils.simpleUUID(), NodeConstant.eventDetectionAndProtectionType));
        }
        //添加事件后果的值
        for (String eventConsequence : eventConsequenceSet) {
            eventConsequenceNodeList.add(new Node(null, eventConsequence, IdUtils.simpleUUID(), NodeConstant.eventConsequenceType));
        }
        //添加处置措施的值
        for (String processMeasure : processMeasureSet) {
            processMeasureNodeList.add(new Node(null, processMeasure, IdUtils.simpleUUID(), NodeConstant.processMeasureType));
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


        //事件探测和保护
        Map<String, Node> eventDetectionAndProtectionMap = eventDetectionAndProtectionNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //事件后果
        Map<String, Node> eventConsequenceMap = eventConsequenceNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));

        //处置措施
        Map<String, Node> processMeasureMap = processMeasureNodeList.stream().collect(Collectors.toMap(Node::getName, node -> node));


        /**
         * 创建所有实体节点
         */
        createAllEntityNode(eventCodeNodeList, eventLevelNodeList, powerPlantUnitNodeList, stackTypeNodeList, directCauseNodeList, rootCauseNodeList,
                involveSystemNodeList, involveFacilityNodeList, eventDetectionAndProtectionNodeList, eventConsequenceNodeList, processMeasureNodeList);


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

        // 事件编码   ==========>  事件级别            级别
        List<Relationship> eventCodeToEventLevelRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  电厂机组            发生
        List<Relationship> eventCodeToPowerPlantUnitRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  堆型               事件堆型
        List<Relationship> eventCodeToStackTypeRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  直接原因            直接原因
        List<Relationship> eventCodeToDirectCauseRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  根本原因            根本原因
        List<Relationship> eventCodeToRootCauseRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  涉及的系统          事件系统
        List<Relationship> eventCodeToInvolveSystemRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  涉及的设备          事件设备
        List<Relationship> eventCodeToInvolveFacilityRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  受影响的设备        受影响的设备
        List<Relationship> eventCodeToAffectFacilityRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  事件探测和保护       探测保护
        List<Relationship> eventCodeToEventDetectionAndProtectionRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  事件后果            后果
        List<Relationship> eventCodeToEventConsequenceRelationshipList = new ArrayList<>();
        // 事件编码   ==========>  处置措施            处置措施
        List<Relationship> eventCodeToProcessMeasureRelationshipList = new ArrayList<>();
        // 电厂机组   ==========>  堆型                属于
        List<Relationship> powerPlantUnitToStackTypeRelationshipList = new ArrayList<>();
        // 堆型      ==========>  系统                 包含
        List<Relationship> stackTypeToInvolveSystemRelationshipList = new ArrayList<>();
        // 系统      ==========>  设备                 包含
        List<Relationship> involveSystemToInvolveFacilityRelationshipList = new ArrayList<>();
        // 设备      ==========>  事件后果             设备的可能后果
        List<Relationship> involveFacilityToEventConsequenceRelationshipList = new ArrayList<>();
        // 设备      ==========>  受影响的设备          可能影响的设备
        List<Relationship> involveFacilityToAffectFacilityRelationshipList = new ArrayList<>();


        /**
         * 需要关系去重的
         *  电厂机组   ==========>  堆型
         *  堆型      ==========>  涉及的系统
         *  涉及的系统   ==========>  涉及的设备
         *  涉及的设备   ==========>  事件后果
         *  涉及的设备   ==========>  受影响设备
         *  涉及的设备   ==========>  涉及的系统
         */

        // 电厂机组   ==========>  堆型
        Set<String> powerPlantUnitToStackTypeSet = new HashSet<>();
        // 堆型      ==========>  涉及的系统
        Set<String> stackTypeToInvolveSystemSet = new HashSet<>();
        // 涉及的系统   ==========>  涉及的设备
        Set<String> InvolveSystemToInvolveFacilitySet = new HashSet<>();
        // 涉及的设备   ==========>  事件后果
        Set<String> InvolveFacilityToEventConsequenceSet = new HashSet<>();
        // 涉及的设备   ==========>  受影响设备
        Set<String> InvolveFacilityToAffectFacilitySet = new HashSet<>();
        // 涉及的设备   ==========>  涉及的系统
        Set<String> InvolveFacilityToInvolveSystemSet = new HashSet<>();


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
                Node eventCodeNode = eventCodeMap.get(eventCode);

                //事件编码   ==========>  事件级别            级别
                if (StringUtils.isNotBlank(eventLevel)) {
                    // 构建 事件编码   ==========>  事件级别 级别
                    Relationship eventLevelRelationship = new Relationship();
                    eventLevelRelationship.setActualId(IdUtils.simpleUUID());

                    //设置开始节点
                    eventLevelRelationship.setStartNode(eventCodeNode);
                    eventLevelRelationship.setName(RelationshipConstant.EVENT_LEVEL_RELATIONSHIP_NAME);

                    //获取事件级别的节点
                    Node eventLevelNode = eventLevelMap.get(eventLevel);
                    eventLevelRelationship.setEndNode(eventLevelNode);

                    //添加 事件编码   ==========>  事件级别 关系节点
                    eventCodeToEventLevelRelationshipList.add(eventLevelRelationship);
                }

                //事件编码   ==========>  电厂机组            发生
                if (StringUtils.isNotBlank(powerPlantUnit)) {
                    // 构建 事件编码   ==========>  电厂机组 发生
                    Relationship powerPlantUnitRelationship = new Relationship();
                    powerPlantUnitRelationship.setActualId(IdUtils.simpleUUID());

                    //设置开始节点
                    powerPlantUnitRelationship.setStartNode(eventCodeNode);
                    powerPlantUnitRelationship.setName(RelationshipConstant.HAPPEN_RELATIONSHIP_NAME);

                    //获取电厂机组的节点
                    Node powerPlantUnitNode = powerPlantUnitMap.get(powerPlantUnit);
                    powerPlantUnitRelationship.setEndNode(powerPlantUnitNode);

                    //添加 事件编码   ==========>  电厂机组 关系节点
                    eventCodeToPowerPlantUnitRelationshipList.add(powerPlantUnitRelationship);
                }

                // 事件编码   ==========>  堆型               事件堆型
                if (StringUtils.isNotBlank(stackType)) {
                    // 构建 事件编码   ==========>  堆型  事件堆型
                    Relationship stackTypeRelationship = new Relationship();
                    stackTypeRelationship.setActualId(IdUtils.simpleUUID());

                    //设置开始节点
                    stackTypeRelationship.setStartNode(eventCodeNode);
                    stackTypeRelationship.setName(RelationshipConstant.EVENT_STACK_TYPE_RELATIONSHIP_NAME);

                    //获取堆型的节点
                    Node stackTypeNode = stackTypeMap.get(stackType);
                    stackTypeRelationship.setEndNode(stackTypeNode);

                    //添加 事件编码   ==========>  堆型 关系节点
                    eventCodeToStackTypeRelationshipList.add(stackTypeRelationship);
                }

                // 事件编码   ==========>  直接原因            直接原因
                if (StringUtils.isNotBlank(directCause)) {
                    // 构建 事件编码   ==========>  直接原因    直接原因
                    Relationship directCauseRelationship = new Relationship();
                    directCauseRelationship.setActualId(IdUtils.simpleUUID());

                    //设置开始节点
                    directCauseRelationship.setStartNode(eventCodeNode);
                    directCauseRelationship.setName(RelationshipConstant.DIRECT_CAUSE_RELATIONSHIP_NAME);

                    //获取直接原因的节点
                    Node directCauseNode = directCauseMap.get(directCause);
                    directCauseRelationship.setEndNode(directCauseNode);

                    //添加 事件编码   ==========>  堆型 关系节点
                    eventCodeToDirectCauseRelationshipList.add(directCauseRelationship);
                }

                // 事件编码   ==========>  根本原因            根本原因
                if (StringUtils.isNotBlank(rootCause)) {
                    // 构建 事件编码   ==========>  根本原因 对象
                    Relationship rootCauseRelationship = new Relationship();
                    rootCauseRelationship.setActualId(IdUtils.simpleUUID());
                    rootCauseRelationship.setName(RelationshipConstant.ROOT_CAUSE_RELATIONSHIP_NAME);

                    //获取根本原因的节点
                    Node rootCauseNode = rootCauseMap.get(rootCause);
                    rootCauseRelationship.setStartNode(eventCodeNode);

                    rootCauseRelationship.setEndNode(rootCauseNode);
                    //添加 事件编码   ==========>  根本原因 关系节点
                    eventCodeToRootCauseRelationshipList.add(rootCauseRelationship);
                }

                // 事件编码   ==========>  涉及的系统          事件系统
                if (StringUtils.isNotBlank(involveSystem)) {
                    // 构建 事件编码   ==========>  涉及的系统    事件系统
                    Relationship involveSystemRelationship = new Relationship();
                    involveSystemRelationship.setActualId(IdUtils.simpleUUID());
                    involveSystemRelationship.setName(RelationshipConstant.INVOLVE_SYSTEM_RELATIONSHIP_NAME);

                    //获取涉及的系统的节点
                    Node involveSystemNode = involveSystemMap.get(involveSystem);
                    involveSystemRelationship.setStartNode(eventCodeNode);

                    involveSystemRelationship.setEndNode(involveSystemNode);
                    //添加 事件编码   ==========>  涉及的系统  关系节点
                    eventCodeToInvolveSystemRelationshipList.add(involveSystemRelationship);
                }

                //事件编码   ==========>  涉及的设备          事件设备
                if (StringUtils.isNotBlank(involveFacilityStr)) {
                    String[] involveFacilityArray = involveFacilityStr.split("、");
                    for (String involveFacility : involveFacilityArray) {
                        // 构建 事件编码   ==========>  涉及的设备   事件设备
                        Relationship involveFacilityRelationship = new Relationship();
                        involveFacilityRelationship.setActualId(IdUtils.simpleUUID());
                        involveFacilityRelationship.setName(RelationshipConstant.INVOLVE_FACILITY_RELATIONSHIP_NAME);

                        //设置开始节点
                        involveFacilityRelationship.setStartNode(eventCodeNode);
                        //获取涉及的设备的节点
                        Node involveFacilityNode = involveFacilityMap.get(involveFacility);
                        involveFacilityRelationship.setEndNode(involveFacilityNode);
                        //添加 事件编码   ==========>  涉及的系统  关系节点
                        eventCodeToInvolveFacilityRelationshipList.add(involveFacilityRelationship);
                    }
                }


                /**
                 * 事件编码   ==========>  受影响的设备        受影响的设备
                 */
                if (StringUtils.isNotBlank(affectFacility)) {
                    // 构建 事件编码   ==========>  受影响的设备  受影响的设备

                    //判断是设备还是系统 目前暂定为存在"、"分割的为设备，其余都是按照系统处理
                    if (affectFacility.contains("、")) {
                        String[] involveFacilityArray = involveFacilityStr.split("、");
                        for (String involveFacility : involveFacilityArray) {
                            // 构建 事件编码   ==========>  涉及的设备   事件设备
                            Relationship affectFacilityRelationship = new Relationship();
                            affectFacilityRelationship.setActualId(IdUtils.simpleUUID());
                            affectFacilityRelationship.setName(RelationshipConstant.AFFECT_FACILITY_RELATIONSHIP_NAME);

                            //设置开始节点
                            affectFacilityRelationship.setStartNode(eventCodeNode);
                            //获取涉及的设备的节点
                            Node involveFacilityNode = involveFacilityMap.get(involveFacility);

                            affectFacilityRelationship.setEndNode(involveFacilityNode);
                            //添加 事件编码   ==========>  涉及的系统  关系节点
                            eventCodeToAffectFacilityRelationshipList.add(affectFacilityRelationship);
                        }

                    } else {
                        Relationship affectSystemRelationship = new Relationship();
                        affectSystemRelationship.setActualId(IdUtils.simpleUUID());
                        affectSystemRelationship.setName(RelationshipConstant.AFFECT_FACILITY_RELATIONSHIP_NAME);
                        //开始节点
                        affectSystemRelationship.setStartNode(eventCodeNode);
                        //结尾节点
                        Node involveSystemNode = involveSystemMap.get(affectFacility);

                        affectSystemRelationship.setEndNode(involveSystemNode);
                        //添加 事件编码   ==========>  受影响的设备   关系节点
                        eventCodeToAffectFacilityRelationshipList.add(affectSystemRelationship);
                    }
                }

                //事件编码   ==========>  事件探测和保护       探测保护
                if (StringUtils.isNotBlank(eventDetectionAndProtection)) {
                    // 构建 事件编码   ==========>  事件探测和保护       探测保护
                    Relationship eventDetectionAndProtectionRelationship = new Relationship();
                    eventDetectionAndProtectionRelationship.setActualId(IdUtils.simpleUUID());
                    eventDetectionAndProtectionRelationship.setName(RelationshipConstant.EVENT_DETECTION_AND_PROTECTION_RELATIONSHIP_NAME);

                    //获取事件探测和保护的节点
                    Node eventDetectionAndProtectionNode = eventDetectionAndProtectionMap.get(eventDetectionAndProtection);
                    eventDetectionAndProtectionRelationship.setStartNode(eventCodeNode);

                    eventDetectionAndProtectionRelationship.setEndNode(eventDetectionAndProtectionNode);
                    //添加 事件编码   ==========>  事件探测和保护   关系节点
                    eventCodeToEventDetectionAndProtectionRelationshipList.add(eventDetectionAndProtectionRelationship);
                }

                //事件编码   ==========>  事件后果            后果
                if (StringUtils.isNotBlank(eventConsequence)) {
                    // 构建 事件编码   ==========>  事件后果     后果
                    Relationship eventConsequenceRelationship = new Relationship();
                    eventConsequenceRelationship.setActualId(IdUtils.simpleUUID());

                    //设置开始节点
                    eventConsequenceRelationship.setStartNode(eventCodeNode);
                    eventConsequenceRelationship.setName(RelationshipConstant.CONSEQUENCE_RELATIONSHIP_NAME);

                    //获取事件后果的节点
                    Node eventConsequenceNode = eventConsequenceMap.get(eventConsequence);
                    eventConsequenceRelationship.setEndNode(eventConsequenceNode);

                    //添加 事件编码   ==========>  事件探测和保护   关系节点
                    eventCodeToEventConsequenceRelationshipList.add(eventConsequenceRelationship);
                }

                //事件编码   ==========>  处置措施            处置措施
                if (StringUtils.isNotBlank(processMeasure)) {
                    // 构建 事件编码   ==========>  处置措施     处置措施
                    Relationship processMeasureRelationship = new Relationship();
                    processMeasureRelationship.setActualId(IdUtils.simpleUUID());

                    //设置开始节点
                    processMeasureRelationship.setStartNode(eventCodeNode);
                    processMeasureRelationship.setName(RelationshipConstant.PROCESS_MEASURE_RELATIONSHIP_NAME);

                    //获取处置措施的节点
                    Node eventConsequenceNode = processMeasureMap.get(processMeasure);
                    processMeasureRelationship.setEndNode(eventConsequenceNode);

                    //添加 事件编码   ==========>  处置措施   关系节点
                    eventCodeToProcessMeasureRelationshipList.add(processMeasureRelationship);
                }
            }


            /**
             *  电厂机组   ==========>  堆型                属于
             *  堆型      ==========>  系统                 包含
             *  系统      ==========>  设备                 包含
             *  设备      ==========>  事件后果             设备的可能后果
             *  设备      ==========>  受影响的设备          可能影响的设备
             */

            // 电厂机组   ==========>  堆型                属于
            if (StringUtils.isNotBlank(powerPlantUnit) && StringUtils.isNotBlank(stackType)) {
                // 构建 电厂机组   ==========>  堆型    属于
                Relationship powerPlantUnitToStackTypeRelationship = new Relationship();
                powerPlantUnitToStackTypeRelationship.setActualId(IdUtils.simpleUUID());
                powerPlantUnitToStackTypeRelationship.setName(RelationshipConstant.BELONG_RELATIONSHIP_NAME);

                //设置开始节点
                Node powerPlantUnitNode = powerPlantUnitMap.get(powerPlantUnit);
                powerPlantUnitToStackTypeRelationship.setStartNode(powerPlantUnitNode);

                //获取结尾节点
                Node stackTypeNode = stackTypeMap.get(stackType);
                powerPlantUnitToStackTypeRelationship.setEndNode(stackTypeNode);

                //去重判断
                String repeatDataJudge = powerPlantUnitNode.getId() + "&" + stackTypeNode.getId();
                if (!powerPlantUnitToStackTypeSet.contains(repeatDataJudge)) {
                    //记录节点以及添加关系
                    powerPlantUnitToStackTypeSet.add(repeatDataJudge);
                    //添加 电厂机组   ==========>  堆型   关系节点
                    powerPlantUnitToStackTypeRelationshipList.add(powerPlantUnitToStackTypeRelationship);
                }
            }

            // 堆型      ==========>  系统                 包含
            if (StringUtils.isNotBlank(stackType) && StringUtils.isNotBlank(involveSystem)) {
                // 构建 堆型      ==========>  系统  包含
                Relationship stackTypeToInvolveSystemRelationship = new Relationship();
                stackTypeToInvolveSystemRelationship.setActualId(IdUtils.simpleUUID());
                stackTypeToInvolveSystemRelationship.setName(RelationshipConstant.STACK_TYPE_CONTAIN_RELATIONSHIP_NAME);

                //设置开始节点
                Node stackTypeNode = stackTypeMap.get(stackType);
                stackTypeToInvolveSystemRelationship.setStartNode(stackTypeNode);

                //获取结尾节点
                Node involveSystemNode = involveSystemMap.get(involveSystem);
                stackTypeToInvolveSystemRelationship.setEndNode(involveSystemNode);

                //去重判断
                String repeatDataJudge = stackTypeNode.getId() + "&" + involveSystemNode.getId();
                if (!stackTypeToInvolveSystemSet.contains(repeatDataJudge)) {
                    //记录节点以及添加关系
                    stackTypeToInvolveSystemSet.add(repeatDataJudge);
                    //添加 堆型      ==========>  系统   关系节点
                    stackTypeToInvolveSystemRelationshipList.add(stackTypeToInvolveSystemRelationship);
                }
            }

            // 系统      ==========>  设备                 包含
            if (StringUtils.isNotBlank(involveSystem) && StringUtils.isNotBlank(involveFacilityStr)) {
                // 构建 系统      ==========>  设备      包含
                String[] involveFacilityArray = involveFacilityStr.split("、");
                for (String involveFacility : involveFacilityArray) {
                    Relationship involveSystemToInvolveFacilityRelationship = new Relationship();
                    involveSystemToInvolveFacilityRelationship.setActualId(IdUtils.simpleUUID());
                    involveSystemToInvolveFacilityRelationship.setName(RelationshipConstant.INVOLVE_SYSTEM_TO_FACILITY_CONTAIN_RELATIONSHIP_NAME);

                    //设置开始节点
                    Node involveSystemNode = involveSystemMap.get(involveSystem);
                    involveSystemToInvolveFacilityRelationship.setStartNode(involveSystemNode);

                    //设置结束节点
                    Node involveFacilityNode = involveFacilityMap.get(involveFacility);
                    involveSystemToInvolveFacilityRelationship.setEndNode(involveFacilityNode);


                    //去重判断
                    String repeatDataJudge = involveSystemNode.getId() + "&" + involveFacilityNode.getId();
                    if (!InvolveSystemToInvolveFacilitySet.contains(repeatDataJudge)) {
                        //记录节点以及添加关系
                        InvolveSystemToInvolveFacilitySet.add(repeatDataJudge);

                        //添加 系统      ==========>  设备   关系节点
                        involveSystemToInvolveFacilityRelationshipList.add(involveSystemToInvolveFacilityRelationship);
                    }
                }
            }

            //设备      ==========>  事件后果             设备的可能后果
            if (StringUtils.isNotBlank(eventConsequence) && StringUtils.isNotBlank(involveFacilityStr)) {
                // 构建 设备      ==========>  事件后果      设备的可能后果
                //获取事件后果
                Node eventConsequenceNode = eventConsequenceMap.get(eventConsequence);

                String[] involveFacilityArray = involveFacilityStr.split("、");
                for (String involveFacility : involveFacilityArray) {
                    Relationship involveFacilityToEventConsequenceRelationship = new Relationship();
                    involveFacilityToEventConsequenceRelationship.setActualId(IdUtils.simpleUUID());
                    involveFacilityToEventConsequenceRelationship.setName(RelationshipConstant.MAY_CAUSE_RELATIONSHIP_NAME);

                    //设置开始节点
                    Node involveFacilityNode = involveFacilityMap.get(involveFacility);
                    involveFacilityToEventConsequenceRelationship.setStartNode(involveFacilityNode);

                    //设置结束节点
                    involveFacilityToEventConsequenceRelationship.setEndNode(eventConsequenceNode);

                    //去重判断
                    String repeatDataJudge = involveFacilityNode.getId() + "&" + eventConsequenceNode.getId();
                    if (!InvolveFacilityToEventConsequenceSet.contains(repeatDataJudge)) {
                        //记录节点以及添加关系
                        InvolveFacilityToEventConsequenceSet.add(repeatDataJudge);
                        //添加 系统      ==========>  设备   关系节点
                        involveFacilityToEventConsequenceRelationshipList.add(involveFacilityToEventConsequenceRelationship);
                    }
                }
            }

            //设备      ==========>  受影响的设备          可能影响的设备
            if (StringUtils.isNotBlank(involveFacilityStr) && StringUtils.isNotBlank(affectFacility)) {
                // 构建 设备      ==========>  受影响的设备          可能影响的设备
                String[] involveFacilityArray = involveFacilityStr.split("、");
                for (String involveFacility : involveFacilityArray) {
                    //获取涉及的设备的节点
                    Node involveFacilityNode = involveFacilityMap.get(involveFacility);

                    //判断是设备还是系统 目前暂定为存在"、"分割的为设备，其余都是按照系统处理
                    if (affectFacility.contains("、") && !affectFacility.equals("反应堆厂房加热、通风和空调系统")) {
                        String[] affectFacilityArray = affectFacility.split("、");
                        for (String facility : affectFacilityArray) {
                            Relationship involveFacilityToAffectFacilityRelationship = new Relationship();
                            involveFacilityToAffectFacilityRelationship.setActualId(IdUtils.simpleUUID());
                            involveFacilityToAffectFacilityRelationship.setName(RelationshipConstant.MAY_EFFECT_FACILITY_RELATIONSHIP_NAME);
                            //开始节点
                            involveFacilityToAffectFacilityRelationship.setStartNode(involveFacilityNode);

                            //结束节点
                            Node facilityNode = involveFacilityMap.get(facility);
                            involveFacilityToAffectFacilityRelationship.setEndNode(facilityNode);
                            if (facilityNode == null) {
                                log.error("involveFacilityStr :" + involveFacilityStr + "======= facility: " + facility);
                            }

                            //去重判断
                            String repeatDataJudge = involveFacilityNode.getId() + "&" + facilityNode.getId();
                            if (!InvolveFacilityToAffectFacilitySet.contains(repeatDataJudge)) {
                                //记录节点以及添加关系
                                InvolveFacilityToAffectFacilitySet.add(repeatDataJudge);

                                //添加 设备   ==========>  系统  关系节点
                                involveFacilityToAffectFacilityRelationshipList.add(involveFacilityToAffectFacilityRelationship);
                            }
                        }
                    } else {

                        Relationship involveFacilityToAffectFacilityRelationship = new Relationship();
                        involveFacilityToAffectFacilityRelationship.setActualId(IdUtils.simpleUUID());
                        involveFacilityToAffectFacilityRelationship.setName(RelationshipConstant.MAY_EFFECT_FACILITY_RELATIONSHIP_NAME);

                        //开始节点
                        involveFacilityToAffectFacilityRelationship.setStartNode(involveFacilityNode);
                        //结尾节点
                        Node involveSystemNode = involveSystemMap.get(affectFacility);
                        involveFacilityToAffectFacilityRelationship.setEndNode(involveSystemNode);
                        if (involveSystemNode == null) {
                            log.error("involveFacilityStr :" + involveFacilityStr + "======= affectFacility: " + affectFacility);
                        }


                        //去重判断
                        String repeatDataJudge = involveFacilityNode.getId() + "&" + involveSystemNode.getId();
                        if (!InvolveFacilityToInvolveSystemSet.contains(repeatDataJudge)) {
                            //记录节点以及添加关系
                            InvolveFacilityToInvolveSystemSet.add(repeatDataJudge);
                            //添加 设备   ==========>  系统  关系节点
                            involveFacilityToAffectFacilityRelationshipList.add(involveFacilityToAffectFacilityRelationship);
                        }
                    }
                }
            }
        }

        //保存所有的关系
        saveAllRelationship(eventCodeToEventLevelRelationshipList, eventCodeToPowerPlantUnitRelationshipList, eventCodeToStackTypeRelationshipList,
                eventCodeToDirectCauseRelationshipList, eventCodeToRootCauseRelationshipList, eventCodeToInvolveSystemRelationshipList,
                eventCodeToInvolveFacilityRelationshipList, eventCodeToAffectFacilityRelationshipList, eventCodeToEventDetectionAndProtectionRelationshipList,
                eventCodeToEventConsequenceRelationshipList, eventCodeToProcessMeasureRelationshipList, powerPlantUnitToStackTypeRelationshipList,
                stackTypeToInvolveSystemRelationshipList, involveSystemToInvolveFacilityRelationshipList, involveFacilityToEventConsequenceRelationshipList,
                involveFacilityToAffectFacilityRelationshipList);


    }

    /**
     * 创建所有实体节点
     *
     * @param eventCodeNodeList                   获取事件编码
     * @param eventLevelNodeList                  事件级别
     * @param powerPlantUnitNodeList              电厂机组
     * @param stackTypeNodeList                   堆型
     * @param directCauseNodeList                 直接原因
     * @param rootCauseNodeList                   根本原因
     * @param involveSystemNodeList               涉及的系统
     * @param involveFacilityNodeList             涉及的设备
     * @param eventDetectionAndProtectionNodeList 事件探测和保护
     * @param eventConsequenceNodeList            事件后果
     * @param processMeasureNodeList              处置措施
     */
    private void createAllEntityNode(List<Node> eventCodeNodeList, List<Node> eventLevelNodeList, List<Node> powerPlantUnitNodeList, List<Node> stackTypeNodeList, List<Node> directCauseNodeList, List<Node> rootCauseNodeList, List<Node> involveSystemNodeList, List<Node> involveFacilityNodeList, List<Node> eventDetectionAndProtectionNodeList, List<Node> eventConsequenceNodeList, List<Node> processMeasureNodeList) {
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
    }

    /**
     * 保存所有的关系
     *
     * @param eventCodeToEventLevelRelationshipList                  事件编码   ==========>  事件级别            级别
     * @param eventCodeToPowerPlantUnitRelationshipList              事件编码   ==========>  电厂机组            发生
     * @param eventCodeToStackTypeRelationshipList                   事件编码   ==========>  堆型               事件堆型
     * @param eventCodeToDirectCauseRelationshipList                 事件编码   ==========>  直接原因            直接原因
     * @param eventCodeToRootCauseRelationshipList                   事件编码   ==========>  根本原因            根本原因
     * @param eventCodeToInvolveSystemRelationshipList               事件编码   ==========>  涉及的系统          事件系统
     * @param eventCodeToInvolveFacilityRelationshipList             事件编码   ==========>  涉及的设备          事件设备
     * @param eventCodeToAffectFacilityRelationshipList              事件编码   ==========>  受影响的设备        受影响的设备
     * @param eventCodeToEventDetectionAndProtectionRelationshipList 事件编码   ==========>  事件探测和保护       探测保护
     * @param eventCodeToEventConsequenceRelationshipList            事件编码   ==========>  事件后果            后果
     * @param eventCodeToProcessMeasureRelationshipList              事件编码   ==========>  处置措施            处置措施
     * @param powerPlantUnitToStackTypeRelationshipList              电厂机组   ==========>  堆型                属于
     * @param stackTypeToInvolveSystemRelationshipList               堆型      ==========>  系统                 包含
     * @param involveSystemToInvolveFacilityRelationshipList         系统      ==========>  设备                 包含
     * @param involveFacilityToEventConsequenceRelationshipList      设备      ==========>  事件后果             设备的可能后果
     * @param involveFacilityToAffectFacilityRelationshipList        设备      ==========>  受影响的设备          可能影响的设备
     */
    private void saveAllRelationship(List<Relationship> eventCodeToEventLevelRelationshipList, List<Relationship> eventCodeToPowerPlantUnitRelationshipList, List<Relationship> eventCodeToStackTypeRelationshipList, List<Relationship> eventCodeToDirectCauseRelationshipList, List<Relationship> eventCodeToRootCauseRelationshipList, List<Relationship> eventCodeToInvolveSystemRelationshipList, List<Relationship> eventCodeToInvolveFacilityRelationshipList, List<Relationship> eventCodeToAffectFacilityRelationshipList, List<Relationship> eventCodeToEventDetectionAndProtectionRelationshipList, List<Relationship> eventCodeToEventConsequenceRelationshipList, List<Relationship> eventCodeToProcessMeasureRelationshipList, List<Relationship> powerPlantUnitToStackTypeRelationshipList, List<Relationship> stackTypeToInvolveSystemRelationshipList, List<Relationship> involveSystemToInvolveFacilityRelationshipList, List<Relationship> involveFacilityToEventConsequenceRelationshipList, List<Relationship> involveFacilityToAffectFacilityRelationshipList) {
        // 事件编码   ==========>  事件级别            级别
        relationshipRepository.saveAll(eventCodeToEventLevelRelationshipList);
        // 事件编码   ==========>  电厂机组            发生
        relationshipRepository.saveAll(eventCodeToPowerPlantUnitRelationshipList);
        // 事件编码   ==========>  堆型               事件堆型
        relationshipRepository.saveAll(eventCodeToStackTypeRelationshipList);
        // 事件编码   ==========>  直接原因            直接原因
        relationshipRepository.saveAll(eventCodeToDirectCauseRelationshipList);
        // 事件编码   ==========>  根本原因            根本原因
        relationshipRepository.saveAll(eventCodeToRootCauseRelationshipList);
        // 事件编码   ==========>  涉及的系统          事件系统
        relationshipRepository.saveAll(eventCodeToInvolveSystemRelationshipList);
        // 事件编码   ==========>  涉及的设备          事件设备
        relationshipRepository.saveAll(eventCodeToInvolveFacilityRelationshipList);
        // 事件编码   ==========>  受影响的设备        受影响的设备
        relationshipRepository.saveAll(eventCodeToAffectFacilityRelationshipList);
        // 事件编码   ==========>  事件探测和保护       探测保护
        relationshipRepository.saveAll(eventCodeToEventDetectionAndProtectionRelationshipList);
        // 事件编码   ==========>  事件后果            后果
        relationshipRepository.saveAll(eventCodeToEventConsequenceRelationshipList);
        // 事件编码   ==========>  处置措施            处置措施
        relationshipRepository.saveAll(eventCodeToProcessMeasureRelationshipList);
        // 电厂机组   ==========>  堆型                属于
        relationshipRepository.saveAll(powerPlantUnitToStackTypeRelationshipList);
        // 堆型      ==========>  系统                 包含
        relationshipRepository.saveAll(stackTypeToInvolveSystemRelationshipList);
        // 系统      ==========>  设备                 包含
        relationshipRepository.saveAll(involveSystemToInvolveFacilityRelationshipList);
        // 设备      ==========>  事件后果             设备的可能后果
        relationshipRepository.saveAll(involveFacilityToEventConsequenceRelationshipList);
        // 设备      ==========>  受影响的设备          可能影响的设备
        relationshipRepository.saveAll(involveFacilityToAffectFacilityRelationshipList);
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
