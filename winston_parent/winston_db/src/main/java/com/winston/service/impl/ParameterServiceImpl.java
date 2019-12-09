package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.Parameter;
import com.winston.entity.ParameterExample;
import com.winston.exception.ErrorException;
import com.winston.mapper.ParameterDao;
import com.winston.mapper.ParameterMapper;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.service.IParameterService;
import com.winston.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ParameterServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/7 16:14
 * @Version：
 */
@Service
public class ParameterServiceImpl implements IParameterService {

    @Autowired
    private ParameterMapper mapper;

    @Autowired
    private ParameterDao parameterDao;

    @Override
    public Result query(Parameter parameter, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        ParameterExample example = new ParameterExample();
        example.setOrderByClause("param_type asc");
        ParameterExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(parameter.getParamName())){
            criteria.andParamNameLike("%"+parameter.getParamName()+"%");
        }
        if(StringUtils.isNotBlank(parameter.getParamType())){
            criteria.andParamTypeEqualTo(parameter.getParamType());
        }
        if(StringUtils.isNotBlank(parameter.getParamValue())){
            criteria.andParamValueEqualTo(parameter.getParamValue());
        }
        List<Parameter> parameters = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(parameters);
        return Result.success(parameters, pageInfo.getTotal());
    }

    @Override
    public Parameter queryById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Parameter queryByNameType(String name, String type) {
        ParameterExample example = new ParameterExample();
        ParameterExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isBlank(name) || StringUtils.isBlank(type)){
            throw new ErrorException(CodeMsg.QUERY_DATA_NUL);
        }
        criteria.andParamNameEqualTo(name).andParamTypeEqualTo(type);
        List<Parameter> parameters = mapper.selectByExample(example);
        if(parameters!= null && parameters.size()>0){
            return parameters.get(0);
        }
        return null;
    }

    @Override
    public List<Parameter> queryParamType() {
        return mapper.selectByExample(new ParameterExample());
    }

    @Override
    public void add(Parameter parameter) {
        mapper.insertSelective(parameter);
    }

    @Override
    public void update(Parameter parameter) {
        mapper.updateByPrimaryKeySelective(parameter);
    }

    @Override
    public void del(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }
}
