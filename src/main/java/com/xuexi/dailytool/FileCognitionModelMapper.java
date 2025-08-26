package com.xuexi.dailytool;

import com.xuexi.dailytool.FileCognitionModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileCognitionModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileCognitionModel record);

    int insertSelective(FileCognitionModel record);

    FileCognitionModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileCognitionModel record);

    int updateByPrimaryKey(FileCognitionModel record);
}