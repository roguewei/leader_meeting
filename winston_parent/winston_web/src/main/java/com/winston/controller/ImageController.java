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
    public void export(HttpServletResponse response, int roomId, int excelId){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        imageService.dropImage(response, roomId, excelId);
    }

}
