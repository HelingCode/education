package cn.cstube.eduservice.service;

import cn.cstube.eduservice.entity.EduCourse;
import cn.cstube.eduservice.entity.vo.CourseInfoForm;
import cn.cstube.eduservice.entity.vo.CoursePublishVo;
import cn.cstube.eduservice.entity.vo.CourseQueryVo;
import cn.cstube.eduservice.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author heling
 * @since 2021-08-24
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishById(String id);

    void delCourseInfo(String id);

    Map<String, Object> getCourseApiPageVo(Page<EduCourse> page, CourseQueryVo courseQueryVo);

    CourseWebVo getCourseWebVo(String id);
}
