package com.xuexi.dailytool;

import com.xuexi.dailytool.FileCognitionModelAndTemplate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileCognitionModelAndTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileCognitionModelAndTemplate record);

    int insertSelective(FileCognitionModelAndTemplate record);

    FileCognitionModelAndTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileCognitionModelAndTemplate record);

    int updateByPrimaryKey(FileCognitionModelAndTemplate record);
}