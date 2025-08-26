package com.xuexi.dailytool;

import com.xuexi.dailytool.FileDataType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDataTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileDataType record);

    int insertSelective(FileDataType record);

    FileDataType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileDataType record);

    int updateByPrimaryKey(FileDataType record);
}