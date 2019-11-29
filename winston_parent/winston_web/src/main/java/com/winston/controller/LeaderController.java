package com.winston.controller;

import com.winston.entity.Leader;
import com.winston.result.Result;
import com.winston.service.ILeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ClassName LeaderController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/27 11:12
 * @Version：
 */
@RestController
@RequestMapping("/web/leader")
public class LeaderController {

    @Autowired
    private ILeaderService leaderService;

    @GetMapping("/sort")
    public Result sort(int roomId, int excelId){
        Map<String, Object> map = leaderService.querySort(roomId, excelId);
        return Result.success(map);
    }

}
