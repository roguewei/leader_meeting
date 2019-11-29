package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.Excel;
import com.winston.entity.ExcelExample;
import com.winston.entity.Leader;
import com.winston.mapper.ExcelMapper;
import com.winston.result.Result;
import com.winston.service.IExcelService;
import com.winston.service.IFileService;
import com.winston.service.ILeaderService;
import com.winston.service.IMeetingLeaderService;
import com.winston.utils.LeaderExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExcelServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/29 9:21
 * @Version：
 */
@Service
public class ExcelServiceImpl implements IExcelService {

    @Autowired
    private ExcelMapper mapper;

    @Autowired
    private LeaderExcelUtil excelUtil;

    @Autowired
    private IFileService fileService;

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IMeetingLeaderService meetingLeaderService;

    @Override
    @Transactional
    public Integer readExcelFile(MultipartFile file) {
        Map<String, String> upload = fileService.upload(file);

        Excel excel = new Excel();
        excel.setName(upload.get("fileName"));
        excel.setPath(upload.get("filePath"));
        Integer excelId = add(excel);

        MultipartFile transport = fileService.transport(excel.getPath());
        List<Leader> excelInfo = excelUtil.getExcelInfo(transport);
        excelInfo.forEach(item -> {
            leaderService.add(item, excelId);
        });
        return excelId;
    }

    @Override
    public Integer add(Excel excel) {
        mapper.insertSelective(excel);
        return excel.getId();
    }

    @Override
    @Transactional
    public void del(Integer id) {
        Excel excel = mapper.selectByPrimaryKey(id);
        fileService.del(excel.getPath());
        mapper.deleteByPrimaryKey(id);
        meetingLeaderService.delByEid(id);
        leaderService.delByEId(id);
    }

    @Override
    public Result query(Excel excel, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        ExcelExample example = new ExcelExample();
        ExcelExample.Criteria criteria = example.createCriteria();
        if(excel.getId() != null && excel.getId() != 0){
            criteria.andIdEqualTo(excel.getId());
        }
        if(StringUtils.isNotBlank(excel.getName())){
            criteria.andNameLike("%"+ excel.getName() +"%");
        }
        List<Excel> excels = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(excels);
        return Result.success(excels, pageInfo.getTotal());
    }

}
