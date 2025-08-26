package com.xuexi.dailytool;

import com.xuexi.dailytool.FileDataTypeModelRel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDataTypeModelRelMapper {
    int insert(FileDataTypeModelRel record);

    int insertSelective(FileDataTypeModelRel record);
}