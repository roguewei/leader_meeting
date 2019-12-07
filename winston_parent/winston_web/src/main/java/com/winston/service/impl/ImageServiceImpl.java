package com.winston.service.impl;

import com.winston.entity.Leader;
import com.winston.entity.Meetingroom;
import com.winston.service.IImageService;
import com.winston.service.ILeaderService;
import com.winston.service.IMeetingRoomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ImageServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/3 9:16
 * @Version：
 */
@Service
public class ImageServiceImpl implements IImageService {

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IMeetingRoomService roomService;

    @Override
    public void dropImage(HttpServletResponse response, int roomId, int excelId) {

        Map<String, Object> querySort = leaderService.querySort(roomId, excelId);
        List<List<Leader>> leaders = (List<List<Leader>>) querySort.get("leaders");

        int width = 80;
        int height = 40;
        //获取图片缓冲区
        BufferedImage image = new BufferedImage(leaders.get(0).size()*90, leaders.size()*50, BufferedImage.TYPE_INT_RGB);
        //得到绘制坏境(这张图片的笔)
        Graphics gh = (Graphics) image.getGraphics();
        //设置颜色
        gh.setColor(Color.WHITE);
        //填充整张图片(其实就是设置背景色)
        gh.fillRect(0, 0, leaders.get(0).size()*90, leaders.size()*50);

        // -------------------------------------------------
        for(int j=0; j<leaders.size(); j++){
            for(int i = 0; i<leaders.get(j).size(); i++){
                gh.setColor(Color.lightGray);
                //绘制一个四边形边框
                int x = i*(width+10);
                int y = j*(height+10);
                gh.drawRect(x, y, 80 - 1, 40 - 1);
                //字体样式 字体格式 字体大小
//                gh.setFont(new Font("Bernard MT", Font.BOLD, 18));
                gh.setFont(new Font("微软雅黑", Font.BOLD, 18));
                //设置字体颜色
                gh.setColor(Color.BLACK);
                //向图片上写随机字符串
                if(StringUtils.isNotBlank(leaders.get(j).get(i).getName())){
                    gh.drawString(leaders.get(j).get(i).getName(), x+19, y+25);
                }else{
                    gh.drawString("", x+19, y+25);
                }
            }
        }
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Pragma", "no-cache");

            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            outputStream = response.getOutputStream();
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
