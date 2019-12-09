package com.winston.service;

import com.winston.entity.Parameter;
import com.winston.result.Result;

import java.util.List;

public interface IParameterService {

    Result query(Parameter parameter, Integer page, Integer length);

    Parameter queryById(Integer id);

    Parameter queryByNameType(String name, String type);

    List<Parameter> queryParamType();

    void add(Parameter parameter);

    void update(Parameter parameter);

    void del(Integer id);

}
