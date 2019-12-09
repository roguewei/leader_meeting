package com.winston.service;

import com.winston.entity.Excel;
import com.winston.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface IExcelService {

    Result query(Excel excel, Integer page, Integer length);

    Excel queryById(Integer id);

    Integer add(Excel excel);

    Integer readExcelFile(MultipartFile file);

    void del(Integer id);

}
