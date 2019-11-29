package com.winston.utils;

import lombok.Data;

/**
 * @ClassName ReadExcel
 * @Author: Winston
 * @Description: Excel文件相关参数
 * @Date:Create：in 2019/10/18 10:52
 * @Version：
 */
@Data
public class ReadExcel {

    //总行数
    private int totalRows = 0;

    //总条数
    private int totalCells = 0;

    //错误信息的收集类
    private String errorMsg;

}
