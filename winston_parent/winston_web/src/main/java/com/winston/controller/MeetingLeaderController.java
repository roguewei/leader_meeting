package com.winston.controller;

import com.winston.entity.MeetingLeader;
import com.winston.result.Result;
import com.winston.service.IMeetingLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MeetingLeaderController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/29 15:32
 * @Version：
 */
@RestController
@RequestMapping("/web/meetingleader")
public class MeetingLeaderController {

    @Autowired
    private IMeetingLeaderService meetingLeaderService;

    @GetMapping
    public Result query(MeetingLeader meetingLeader){
        List<MeetingLeader> query = meetingLeaderService.query(meetingLeader);
        return Result.success(query.size());
    }

    @PostMapping("/add")
    public Result add(@RequestBody MeetingLeader meetingLeader){
        meetingLeaderService.add(meetingLeader);
        return Result.success("添加成功！");
    }

    @GetMapping("/delByMid")
    public Result delByMid(Integer mId){
        meetingLeaderService.delByMid(mId);
        return Result.success("删除成功！");
    }

}
