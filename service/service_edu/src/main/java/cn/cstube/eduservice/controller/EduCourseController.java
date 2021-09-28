package cn.cstube.eduservice.controller;


import cn.cstube.commonutils.R;
import cn.cstube.eduservice.entity.EduCourse;
import cn.cstube.eduservice.entity.EduTeacher;
import cn.cstube.eduservice.entity.vo.CourseInfoForm;
import cn.cstube.eduservice.entity.vo.CoursePublishVo;
import cn.cstube.eduservice.entity.vo.CourseQuery;
import cn.cstube.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author heling
 * @since 2021-08-24
 */
@Api(description="课程管理")
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId = eduCourseService.addCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }

    @ApiOperation(value = "根据id查询课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable String id){
        CourseInfoForm courseInfoForm = eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoForm);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfo(courseInfoForm);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id查询课程发布信息")
    @GetMapping("getCoursePublishById/{id}")
    public R getCoursePublishById(@PathVariable String id){
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishById(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = eduCourseService.getById(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }


    @ApiOperation(value = "查询所有课程信息")
    @GetMapping("getCourseInfo")
    public R getCourseInfo(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);

    }

    @ApiOperation(value = "分页查询课程信息")
    @GetMapping("getCoursePage/{current}/{limit}")
    public R getCoursePage(@PathVariable Long current,
                           @PathVariable Long limit){
        Page<EduCourse> page = new Page<>(current,limit);
        eduCourseService.page(page,null);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("list",records).data("total",total);
    }

    @ApiOperation(value = "带条件分页查询课程信息")
    @PostMapping("getCoursePageVo/{current}/{limit}")
    public R getCoursePageVo(@PathVariable Long current,
                             @PathVariable Long limit,
                             @RequestBody CourseQuery courseQuery){

        String title = courseQuery.getTitle();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        //判断条件是否为空，创建条件构造器
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        Page<EduCourse> page = new Page<>(current,limit);
        eduCourseService.page(page,wrapper);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("list",records).data("total",total);

    }

    @ApiOperation(value = "根据id删除课程相关信息")
    @DeleteMapping("delCourseInfo/{id}")
    public R delCourseInfo(@PathVariable String id){
        eduCourseService.delCourseInfo(id);
        return R.ok();
    }



}

