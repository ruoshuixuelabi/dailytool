package com.xuexi.dailytool;

import lombok.Data;

/**
 * 文件识别模型表
 */
@Data
public class FileCognitionModel {
    private Integer id;

    /**
    * 模型名称
    */
    private String name;

    /**
    * 来源：1.内置；2.自定义
    */
    private Integer source;

    /**
    * 状态：1.启用；2.停用
    */
    private Integer status;

    /**
    * 备注
    */
    private String remark;

    /**
    * 设置敏感类型 0-以识别特征为准 1-敏感 2-不敏感
    */
    private Integer sensitiveType;
}