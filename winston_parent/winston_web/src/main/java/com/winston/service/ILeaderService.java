package com.winston.service;

import com.winston.entity.Leader;

import java.util.List;
import java.util.Map;

public interface ILeaderService {

    // 插入数据
    void add(Leader leader, Integer excelid);

    // 查询数据
    List<Leader> query(Leader leader);

    List<Leader> queryInIds(List<Integer> Ids);

    // 查询数据排列
    Map<String, Object> querySort(int roomId, int excelId);

    void delByEId(Integer eId);

}
