package com.winston.service;

import com.winston.entity.Meeting;
import com.winston.result.Result;

import java.util.Map;

public interface IMeetingService {

    Result query(Meeting meeting, Integer page, Integer length);

    Map<String, Object> queryByRid(Integer rId);

    Map<String, Object> queryById(Integer id);

    void add(Meeting meeting);

    void update(Meeting meeting);

    void del(Integer id);
}
