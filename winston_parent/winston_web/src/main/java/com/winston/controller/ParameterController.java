package com.winston.controller;

import com.winston.entity.Parameter;
import com.winston.result.Result;
import com.winston.service.IParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ParameterController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/7 16:35
 * @Version：
 */
@RestController
@RequestMapping("/web/parameter")
public class ParameterController {

    @Autowired
    private IParameterService parameterService;

    @GetMapping
    public Result query(Parameter parameter, Integer page, Integer length){
        return parameterService.query(parameter, page, length);
    }

    @GetMapping("/queryById")
    public Result queryById(Integer id){
        Parameter query = parameterService.queryById(id);
        return Result.success(query);
    }

    @GetMapping("/queryByNameType")
    public Result queryByNameType(String name, String type){
        Parameter query = parameterService.queryByNameType(name, type);
        return Result.success(query);
    }

    @GetMapping("/queryParamType")
    public Result queryParamType(){
        List<Parameter> list = parameterService.queryParamType();
        return Result.success(list);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Parameter parameter){
        parameterService.add(parameter);
        return Result.success("添加成功！");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Parameter parameter){
        parameterService.update(parameter);
        return Result.success("修改成功！");
    }

    @GetMapping("/del")
    public Result del(Integer id){
        parameterService.del(id);
        return Result.success("删除成功！");
    }

}
