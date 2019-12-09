package com.winston.controller;

import com.winston.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName ImageController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/3 9:21
 * @Version：
 */
@RestController
@RequestMapping("/web/image")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping("/export")
    public void export(HttpServletResponse response, int mId){
        imageService.dropImage(response, mId);
    }

}
