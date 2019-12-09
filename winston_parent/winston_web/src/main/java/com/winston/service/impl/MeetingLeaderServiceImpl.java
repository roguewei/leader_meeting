package com.winston.service.impl;

import com.winston.entity.Leader;
import com.winston.entity.MeetingLeader;
import com.winston.entity.MeetingLeaderExample;
import com.winston.entity.Meetingroom;
import com.winston.mapper.MeetingLeaderMapper;
import com.winston.service.ILeaderService;
import com.winston.service.IMeetingLeaderService;
import com.winston.service.IMeetingRoomService;
import com.winston.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MeetingLeaderServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/29 10:40
 * @Version：
 */
@Service
public class MeetingLeaderServiceImpl implements IMeetingLeaderService {

    @Autowired
    private MeetingLeaderMapper mapper;

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IMeetingService meetingService;

    @Override
    public List<MeetingLeader> query(MeetingLeader meetingLeader) {
        MeetingLeaderExample example = new MeetingLeaderExample();
        example.setOrderByClause("e_id asc");
        MeetingLeaderExample.Criteria criteria = example.createCriteria();
        if(meetingLeader.getmId() != null && meetingLeader.getmId() != 0){
            criteria.andMIdEqualTo(meetingLeader.getmId());
        }
        if(meetingLeader.geteId() != null && meetingLeader.geteId() != 0){
            criteria.andEIdEqualTo(meetingLeader.geteId());
        }
        List<MeetingLeader> meetingLeaders = mapper.selectByExample(example);
        return meetingLeaders;
    }

    @Override
    public void add(MeetingLeader meetingLeader) {
        Leader leader = new Leader();
        leader.setExcelId(meetingLeader.geteId());
        List<Leader> Leaders = leaderService.query(leader);
        Map<String, Object> meeting = meetingService.queryById(meetingLeader.getmId());
        Leaders.forEach(item -> {
            meetingLeader.setlId(item.getId());
            meetingLeader.setmName((String) meeting.get("name"));
            mapper.insertSelective(meetingLeader);
        });
    }

    @Override
    public void delByEid(Integer eId) {
        MeetingLeaderExample example = new MeetingLeaderExample();
        example.createCriteria().andEIdEqualTo(eId);
        mapper.deleteByExample(example);
    }

    @Override
    public void delByMid(Integer mId) {
        MeetingLeaderExample example = new MeetingLeaderExample();
        example.createCriteria().andMIdEqualTo(mId);
        List<MeetingLeader> meetingLeaders = mapper.selectByExample(example);

        if(meetingLeaders != null && meetingLeaders.size() > 0){
            // 删除leader表对应数据
            leaderService.delByEId(meetingLeaders.get(0).geteId());

            // 删除meeting表对应数据
            mapper.deleteByExample(example);
        }
    }

    @Override
    public void delLeader(Integer mId) {
    }
}
