package com.hr.neo4j.util;

import com.hr.neo4j.base.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 节点类型
 */
public class NodeConstant {

    //获取事件编码
    public static final String eventCodeType = "0";

    //事件级别
    public static final String eventLevelType = "1";

    //电厂机组
    public static final String powerPlantUnitType = "2";

    //堆型
    public static final String stackTypeType = "3";

    //直接原因
    public static final String directCauseType = "4";

    //根本原因
    public static final String rootCauseType = "5";

    //涉及的系统
    public static final String involveSystemType = "6";

    //涉及的设备
    public static final String involveFacilityType = "7";

    //事件探测和保护
    public static final String eventDetectionAndProtectionType = "8";

    //事件后果
    public static final String eventConsequenceType = "9";

    //处置措施
    public static final String processMeasureType = "10";
}
