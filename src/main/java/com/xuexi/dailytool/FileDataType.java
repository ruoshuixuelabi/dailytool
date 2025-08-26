package com.xuexi.dailytool;

import lombok.Data;

/**
 * 文件识别特征表
 */
@Data
public class FileDataType {
    /**
    * 主键id
    */
    private Integer id;

    /**
    * 名称
    */
    private String name;

    /**
    * 匹配类型
    */
    private String matchType;

    /**
    * 数据结构
    */
    private String dataStructure;

    /**
    * 来源 1内置；2自定义
    */
    private Integer sourceType;

    /**
    * 是否敏感 1-是 0-否
    */
    private Integer isSensitive;
}