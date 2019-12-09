package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.Meeting;
import com.winston.entity.MeetingExample;
import com.winston.entity.Meetingroom;
import com.winston.exception.ErrorException;
import com.winston.mapper.MeetingMapper;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.service.IMeetingLeaderService;
import com.winston.service.IMeetingRoomService;
import com.winston.service.IMeetingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName MeetingServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/7 18:30
 * @Version：
 */
@Service
public class MeetingServiceImpl implements IMeetingService {

    @Autowired
    private MeetingMapper mapper;

    @Autowired
    private IMeetingRoomService roomService;

    @Autowired
    private IMeetingLeaderService mlService;

    @Override
    public Result query(Meeting meeting, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        MeetingExample example = new MeetingExample();
        example.setOrderByClause("create_time desc");
        MeetingExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(meeting.getName())){
            criteria.andNameLike("%"+meeting.getName()+"%");
        }
        List<Meeting> meetings = mapper.selectByExample(example);

        if(meetings == null || meetings.size() <= 0){
            throw new ErrorException(CodeMsg.QUERY_DATA_NUL);
        }
        List<Map<String, Object>> result = new ArrayList<>();
        meetings.forEach(item -> {
            Map<String, Object> roomMap = getRoomMap(item.getrId());
            roomMap.put("name", item.getName());
            roomMap.put("id", item.getId());
            result.add(roomMap);
        });
        PageInfo pageInfo = new PageInfo(meetings);
        return Result.success(result, pageInfo.getTotal());
    }

    @Override
    public Map<String, Object> queryByRid(Integer rId) {
        MeetingExample example = new MeetingExample();
        example.createCriteria().andRIdEqualTo(rId);
        List<Meeting> meetings = mapper.selectByExample(example);
        if(meetings != null && meetings.size() > 0){
            Map<String, Object> roomMap = getRoomMap(meetings.get(0).getrId());
            roomMap.put("name", meetings.get(0).getName());
            roomMap.put("id", meetings.get(0).getId());
            return roomMap;
        }
        return null;
    }

    @Override
    public Map<String, Object> queryById(Integer id) {
        Meeting meeting = mapper.selectByPrimaryKey(id);
        Map<String, Object> roomMap = getRoomMap(meeting.getrId());
        roomMap.put("name", meeting.getName());
        roomMap.put("id", meeting.getId());
        return roomMap;
    }

    @Override
    public void add(Meeting meeting) {
        long nowTime = new Date().getTime();
        meeting.setCreateTime(nowTime);
        mapper.insertSelective(meeting);
    }

    @Override
    public void update(Meeting meeting) {
        long nowTime = new Date().getTime();
        meeting.setCreateTime(nowTime);
        mapper.updateByPrimaryKeySelective(meeting);
    }

    @Override
    public void del(Integer id) {
        mlService.delByMid(id);
        mapper.deleteByPrimaryKey(id);
    }

    private Map<String, Object> getRoomMap(Integer roomId){
        Map<String, Object> result = new HashMap<>();

        Meetingroom meetingroom = roomService.queryById(roomId);
        result.put("roomName", meetingroom.getName());
        result.put("num", meetingroom.getNum());
        result.put("row", meetingroom.getRow());
        result.put("col", meetingroom.getCol());
        result.put("rId", meetingroom.getId());
        return result;
    }
}
