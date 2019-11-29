package com.winston.service.impl;

import com.winston.entity.*;
import com.winston.exception.ErrorException;
import com.winston.mapper.LeaderMapper;
import com.winston.result.CodeMsg;
import com.winston.service.*;
import com.winston.utils.LeaderExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @ClassName LeaderServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/27 11:08
 * @Version：
 */
@Service
@Transactional
public class LeaderServiceImpl implements ILeaderService {

    @Autowired
    private LeaderMapper mapper;

    @Autowired
    private IMeetingRoomService roomService;

    @Autowired
    private IMeetingLeaderService meetingLeaderService;

    public void add(Leader leader, Integer excelid) {
        leader.setExcelId(excelid);
        mapper.insertSelective(leader);
    }

    @Override
    public List<Leader> query(Leader leader) {
        LeaderExample example = new LeaderExample();
        example.setOrderByClause("priority asc");
        LeaderExample.Criteria criteria = example.createCriteria();
        if(leader.getExcelId() != null && leader.getExcelId() != 0){
            criteria.andExcelIdEqualTo(leader.getExcelId());
        }
        List<Leader> leaders = mapper.selectByExample(example);
        return leaders;
    }

    @Override
    public List<Leader> queryInIds(List<Integer> Ids) {
        if(Ids == null || Ids.size()==0){
            return null;
        }
        LeaderExample example = new LeaderExample();
        example.setOrderByClause("priority asc");
        example.createCriteria().andIdIn(Ids);
        List<Leader> leaders = mapper.selectByExample(example);
        return leaders;
    }

    @Override
    public Map<String, Object> querySort(int roomId, int excelId) {
        MeetingLeader meetingLeader = new MeetingLeader();
        meetingLeader.setmId(roomId);
        meetingLeader.seteId(excelId);
        List<MeetingLeader> meetingLeaders = meetingLeaderService.query(meetingLeader);

        List<Integer> lIds = new ArrayList<>();
        meetingLeaders.forEach(item -> {
            lIds.add(item.getlId());
        });
        List<Leader> query = queryInIds(lIds);

        Meetingroom meetingroom = roomService.queryById(roomId);

        List<List<Leader>> result = new ArrayList<>();

        if(query == null || query.size() <=0){
            throw new ErrorException(CodeMsg.QUERY_DATA_NUL);
        }
        for(int j=1; j<=meetingroom.getRow(); j++){
            List<Leader> sortClo = new ArrayList<>();
            for(int i=(j-1)*meetingroom.getCol(); i<meetingroom.getCol()*j; i++){
                if(i == 0){
                    sortClo.add(query.get(i));
                }else{
                    if(i%2 == 0){
                        if(i<query.size()){
                            sortClo.add(query.get(i));
                        }
                        else{
                            sortClo.add(new Leader());
                        }
                    }else{
                        if(i<query.size()){
                            sortClo.add(0, query.get(i));
                        }
                        else{
                            sortClo.add(0, new Leader());
                        }
                    }
                }
            }
            result.add(sortClo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("leaders", result);
        map.put("meetingroom", meetingroom);
        return map;
    }

    @Override
    public void delByEId(Integer eId) {
        LeaderExample example = new LeaderExample();
        example.createCriteria().andExcelIdEqualTo(eId);
        mapper.deleteByExample(example);
    }

}
