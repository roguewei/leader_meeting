package com.winston.controller;

import com.winston.entity.Meetingroom;
import com.winston.result.Result;
import com.winston.service.IMeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MeetingRoomController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/28 17:09
 * @Version：
 */
@RestController
@RequestMapping("/web/meetingroom")
public class MeetingRoomController {

    @Autowired
    private IMeetingRoomService meetingRoomService;

    @GetMapping
    public Result query(Meetingroom meetingroom, Integer page, Integer length){
        return meetingRoomService.query(meetingroom, page, length);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        Meetingroom meetingroom = meetingRoomService.queryById(id);
        return Result.success(meetingroom);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Meetingroom meetingroom){
        meetingRoomService.add(meetingroom);
        return Result.success("添加成功！");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Meetingroom meetingroom){
        meetingRoomService.update(meetingroom);
        return Result.success("修改成功！");
    }

    @PostMapping("/del")
    public Result del(Integer id){
        meetingRoomService.del(id);
        return Result.success("删除成功！");
    }

}
