package cn.cstube.eduservice.controller;


import cn.cstube.commonutils.R;
import cn.cstube.eduservice.entity.vo.OneSubjectVo;
import cn.cstube.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author heling
 * @since 2021-08-24
 */
@Api(description="课程分类管理")
@RestController
@RequestMapping("/eduservice/edusubject")
@CrossOrigin("*")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation(value = "批量导入课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.addSubject(file,subjectService);
        return R.ok();

    }

    @ApiOperation(value = "查询所有课程分类")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubjectVo> allSubjectList = subjectService.getAllSubject();
        return R.ok().data("allSubject",allSubjectList);
    }




}

