package com.winston.service;

import com.winston.entity.Meetingroom;
import com.winston.result.Result;

import java.util.List;

public interface IMeetingRoomService {

    Result query(Meetingroom meetingroom, Integer page, Integer length);

    Meetingroom queryById(Integer id);

    void add(Meetingroom meetingroom);

    void update(Meetingroom meetingroom);

    void del(Integer id);

}
