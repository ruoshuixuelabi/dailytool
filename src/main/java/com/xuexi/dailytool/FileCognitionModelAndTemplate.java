package com.xuexi.dailytool;

import lombok.Data;

/**
 * 文件识别模型关联分级分类模板信息表
 */
@Data
public class FileCognitionModelAndTemplate {
    private Integer id;

    /**
    * 识别模型id
    */
    private Integer modelId;

    /**
    * 关联模板id
    */
    private Integer templateId;

    /**
    * 关联分类id
    */
    private Integer categoryId;

    /**
    * 关联分级id
    */
    private Integer levelId;
}