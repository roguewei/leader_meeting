package com.winston.service;

import com.winston.entity.MeetingLeader;

import java.util.List;

public interface IMeetingLeaderService {

    List<MeetingLeader> query(MeetingLeader meetingLeader);

    void add(MeetingLeader meetingLeader);

    void delByEid(Integer eId);

    void delByMid(Integer mId);
}
