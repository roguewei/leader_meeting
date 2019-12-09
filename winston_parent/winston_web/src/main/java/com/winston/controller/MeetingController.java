package com.winston.controller;

import com.winston.entity.Meeting;
import com.winston.result.Result;
import com.winston.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName MeetingController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/7 18:32
 * @Version：
 */
@RestController
@RequestMapping("/web/meeting")
public class MeetingController {

    @Autowired
    private IMeetingService meetingService;

    @GetMapping
    public Result query(Meeting meeting, Integer page, Integer length){
        return meetingService.query(meeting, page, length);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        Map<String, Object> meeting = meetingService.queryById(id);
        return Result.success(meeting);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Meeting meeting){
        meetingService.add(meeting);
        return Result.success("添加成功！");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Meeting meeting){
        meetingService.update(meeting);
        return Result.success("修改成功！");
    }

    @GetMapping("/del")
    public Result del(Integer id){
        meetingService.del(id);
        return Result.success("删除成功！");
    }

}
