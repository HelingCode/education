package cn.cstube.eduservice.service.impl;

import cn.cstube.baseservice.handler.GuliException;
import cn.cstube.eduservice.client.VodClient;
import cn.cstube.eduservice.entity.EduChapter;
import cn.cstube.eduservice.entity.EduCourse;
import cn.cstube.eduservice.entity.EduCourseDescription;
import cn.cstube.eduservice.entity.EduVideo;
import cn.cstube.eduservice.entity.vo.CourseInfoForm;
import cn.cstube.eduservice.entity.vo.CoursePublishVo;
import cn.cstube.eduservice.entity.vo.CourseQueryVo;
import cn.cstube.eduservice.entity.vo.CourseWebVo;
import cn.cstube.eduservice.mapper.EduCourseMapper;
import cn.cstube.eduservice.service.EduChapterService;
import cn.cstube.eduservice.service.EduCourseDescriptionService;
import cn.cstube.eduservice.service.EduCourseService;
import cn.cstube.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author heling
 * @since 2021-08-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Resource
    private VodClient vodClient;


    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {
        //1 添加课程信息

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert==0){
            throw new GuliException(20001,"创建课程失败");
        }

        //2获取课程id
        String courseId = eduCourse.getId();
        //3添加课程描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseId);
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionService.save(courseDescription);

        return courseId;
    }

    //根据id查询课程信息
    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        //1根据id查询课程信息
        EduCourse eduCourse = baseMapper.selectById(id);

        //2封装课程信息
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        //3根据id查询课程描述信息
        EduCourseDescription courseDescription = courseDescriptionService.getById(id);


        //4封装课程描述
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }


    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //1复制课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        //2更新课程数据
        int update = baseMapper.updateById(eduCourse);
        //3判断是否成功
        if(update==0){
            throw new GuliException(20001,"修改课程失败");
        }
        //4更新课程描述
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionService.updateById(eduCourseDescription);


    }

    @Override
    public CoursePublishVo getCoursePublishById(String id) {

        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishById(id);


        return coursePublishVo;
    }

    @Override
    public void delCourseInfo(String id) {
        //删除视频
       QueryWrapper<EduVideo> videoIdWrapper = new QueryWrapper<>();
       videoIdWrapper.eq("course_id",id);
        List<EduVideo> list = eduVideoService.list(videoIdWrapper);
        List<String> videoIdList = new ArrayList<>();

        for (EduVideo eduVideo : list) {
            videoIdList.add(eduVideo.getVideoSourceId());
        }

        if(videoIdList.size()>0){
            vodClient.delVideoList(videoIdList);
        }

        //删除小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        eduVideoService.remove(videoWrapper);

        //删除章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",id);
        eduChapterService.remove(chapterWrapper);

        //删除课程描述
        courseDescriptionService.removeById(id);

        //删除课程
        int delete = baseMapper.deleteById(id);
        if(delete == 0){
            throw new GuliException(20001,"删除课程失败");
        }

    }

    //带条件分页查询课程列表
    @Override
    public Map<String, Object> getCourseApiPageVo(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        //1 取出查询条件
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();
        //2 验空，不为空拼写到查询条件
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(buyCountSort)){
            queryWrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(gmtCreateSort)){
            queryWrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(priceSort)){
            queryWrapper.orderByDesc("price");
        }
        queryWrapper.eq("status","Normal");
        //3 分页查询
        baseMapper.selectPage(pageParam,queryWrapper);
        //4 封装数据
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //根据课程id查询课程相关信息
    @Override
    public CourseWebVo getCourseWebVo(String id) {
        CourseWebVo courseWebVo = baseMapper.getCourseWebVo(id);



        return courseWebVo;
    }
}
