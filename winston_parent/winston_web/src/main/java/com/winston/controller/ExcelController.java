package com.winston.controller;

import com.winston.entity.Excel;
import com.winston.result.Result;
import com.winston.service.IExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName ExcelController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/29 10:24
 * @Version：
 */
@RestController
@RequestMapping("/web/excel")
public class ExcelController {

    @Autowired
    private IExcelService excelService;

    @GetMapping
    public Result query(Excel excel, Integer page, Integer length){
        return excelService.query(excel, page, length);
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        Integer excelId = excelService.readExcelFile(file);
        return Result.success(excelId);
    }

    @GetMapping("/del")
    public Result del(Integer id){
        excelService.del(id);
        return Result.success("删除成功!");
    }

}
