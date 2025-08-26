package com.xuexi.dailytool;

import lombok.Data;

/**
 * 文件识别模型与文件识别特征关联表
 */
@Data
public class FileDataTypeModelRel {
    /**
    * 识别模型id
    */
    private Integer modelId;

    /**
    * 识别特征id
    */
    private Integer typeId;
}