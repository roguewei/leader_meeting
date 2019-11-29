package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.Meetingroom;
import com.winston.entity.MeetingroomExample;
import com.winston.mapper.MeetingroomMapper;
import com.winston.result.Result;
import com.winston.service.IMeetingLeaderService;
import com.winston.service.IMeetingRoomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MeetingRoomServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/28 17:04
 * @Version：
 */
@Service
public class MeetingRoomServiceImpl implements IMeetingRoomService {

    @Autowired
    private MeetingroomMapper mapper;

    @Autowired
    private IMeetingLeaderService meetingLeaderService;

    @Override
    public Result query(Meetingroom meetingroom, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        MeetingroomExample example = new MeetingroomExample();
        example.setOrderByClause("num asc");
        MeetingroomExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(meetingroom.getName())){
            criteria.andNameLike("%" + meetingroom.getName() +"%");
        }
        List<Meetingroom> meetingrooms = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(meetingrooms);
        return Result.success(meetingrooms, pageInfo.getTotal());
    }

    @Override
    public Meetingroom queryById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Meetingroom meetingroom) {
        mapper.insertSelective(meetingroom);
    }

    @Override
    public void update(Meetingroom meetingroom) {
        mapper.updateByPrimaryKeySelective(meetingroom);
    }

    @Override
    public void del(Integer id) {
        mapper.deleteByPrimaryKey(id);
        meetingLeaderService.delByMid(id);
    }


}
