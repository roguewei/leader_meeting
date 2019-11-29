package com.winston.controller;

import com.winston.entity.MeetingLeader;
import com.winston.result.Result;
import com.winston.service.IMeetingLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public Result add(@RequestBody MeetingLeader meetingLeader){
        meetingLeaderService.add(meetingLeader);
        return Result.success("添加成功！");
    }

}
