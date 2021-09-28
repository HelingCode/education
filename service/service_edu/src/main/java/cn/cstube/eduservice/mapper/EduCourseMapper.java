package cn.cstube.eduservice.mapper;

import cn.cstube.eduservice.entity.EduCourse;
import cn.cstube.eduservice.entity.vo.CoursePublishVo;
import cn.cstube.eduservice.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author heling
 * @since 2021-08-24
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishById(String id);

    CourseWebVo getCourseWebVo(String id);
}
