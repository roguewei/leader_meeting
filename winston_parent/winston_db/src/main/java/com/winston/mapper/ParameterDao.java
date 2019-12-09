package com.winston.mapper;

import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface ParameterDao {

    @Select("select param_type from parameter GROUP BY param_type")
    List<String> queryParamType();

}
