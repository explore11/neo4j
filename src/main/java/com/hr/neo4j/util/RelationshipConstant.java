package com.hr.neo4j.util;

/**
 * 关系实体名称
 */
public class RelationshipConstant {
    /**
     * 事件编码   ==========>  事件级别            类别
     */
    public static final String EVENT_LEVEL_RELATIONSHIP_NAME = "类别";

    /**
     * 事件编码   ==========>  电厂机组            发生
     */
    public static final String HAPPEN_RELATIONSHIP_NAME = "发生";
    /**
     * 事件编码   ==========>  堆型               事件堆型
     */
    public static final String EVENT_STACK_TYPE_RELATIONSHIP_NAME = "事件堆型";

    /**
     * 事件编码   ==========>  直接原因            直接原因
     */
    public static final String DIRECT_CAUSE_RELATIONSHIP_NAME = "直接原因";

    /**
     * 事件编码   ==========>  根本原因            根本原因
     */
    public static final String ROOT_CAUSE_RELATIONSHIP_NAME = "根本原因";

    /**
     * 事件编码   ==========>  涉及的系统          事件系统
     */
    public static final String INVOLVE_SYSTEM_RELATIONSHIP_NAME = "事件系统";

    /**
     * 事件编码   ==========>  涉及的设备          事件设备
     */
    public static final String INVOLVE_FACILITY_RELATIONSHIP_NAME = "事件设备";

    /**
     * 事件编码   ==========>  受影响的设备        受影响的设备
     */
    public static final String AFFECT_FACILITY_RELATIONSHIP_NAME = "受影响的设备";

    /**
     * 事件编码   ==========>  事件探测和保护       探测保护
     */
    public static final String EVENT_DETECTION_AND_PROTECTION_RELATIONSHIP_NAME = "探测保护";

    /**
     * 事件编码   ==========>  事件后果            后果
     */
    public static final String CONSEQUENCE_RELATIONSHIP_NAME = "后果";

    /**
     * 事件编码   ==========>  处置措施            处置措施
     */
    public static final String PROCESS_MEASURE_RELATIONSHIP_NAME = "处置措施";


    /**
     * 电厂机组   ==========>  堆型                属于
     */

    public static final String BELONG_RELATIONSHIP_NAME = "属于";


    /**
     * 堆型      ==========>  系统                 包含
     */
    public static final String STACK_TYPE_CONTAIN_RELATIONSHIP_NAME = "包含";

    /**
     * 系统      ==========>  设备                 包含
     */
    public static final String STACK_TYPE_TO_FACILITY_CONTAIN_RELATIONSHIP_NAME = "包含";

    /**
     * 设备      ==========>  事件后果             设备的可能后果
     */
    public static final String MAY_CAUSE_RELATIONSHIP_NAME = "设备的可能后果";


    /**
     * 设备      ==========>  受影响的设备          可能影响的设备
     */
    public static final String MAY_EFFECT_FACILITY_RELATIONSHIP_NAME = "可能影响的设备";


}
