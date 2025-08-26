package com.xuexi.dailytool;

import lombok.Data;

/**
 * 文件识别规则表
 */
@Data
public class FileDataRule {
    /**
    * 主键id
    */
    private Integer id;

    /**
    * 连接符
    */
    private String operator;

    /**
    * 属性名
    */
    private String attributeName;

    /**
    * 匹配方式
    */
    private String condition;

    /**
    * 属性值
    */
    private String attributeValue;

    /**
    * 规则组id
    */
    private Integer groupId;

    /**
    * 关联类型 1-模型 2-特征
    */
    private Integer relType;

    /**
    * 关联id
    */
    private Integer relId;
}