package cn.cstube.eduservice.api;

import cn.cstube.commonutils.R;
import cn.cstube.commonutils.utils.JwtUtils;
import cn.cstube.commonutils.vo.CourseWebVoForOrder;
import cn.cstube.eduservice.client.OrderClient;
import cn.cstube.eduservice.entity.EduCourse;
import cn.cstube.eduservice.entity.EduTeacher;
import cn.cstube.eduservice.entity.vo.ChapterVo;
import cn.cstube.eduservice.entity.vo.CourseQuery;
import cn.cstube.eduservice.entity.vo.CourseQueryVo;
import cn.cstube.eduservice.entity.vo.CourseWebVo;
import cn.cstube.eduservice.service.EduChapterService;
import cn.cstube.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @auther heling
 * @date 2021/9/25
 */

@Api(description="前台课程展示")
@RestController
@RequestMapping("/eduservice/courseapi")
@CrossOrigin("*")
public class CourseApiController {


    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "带条件分页查询课程列表")
    @PostMapping("getCourseApiPageVo/{current}/{limit}")
    public R getCourseApiPageVo(@PathVariable Long current,
                             @PathVariable Long limit,
                             @RequestBody CourseQueryVo courseQueryVo){

        Page<EduCourse> page = new Page<>(current,limit);
        Map<String,Object> map = courseService.getCourseApiPageVo(page,courseQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据课程id查询课程相关信息")
    @GetMapping("getCourseWebInfo/{courseId}")
    public R getCourseWebInfo(@PathVariable String courseId,
                              HttpServletRequest request){
        //1 查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        //2查询课程大纲信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoById(courseId);
        //3根据课程id、用户id查询是已购买,远程调用
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("memberId ="+memberId);
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, memberId);

        return R.ok().data("courseWebVo",courseWebVo)
                .data("chapterVideoList",chapterVideoList)
                .data("isBuyCourse",isBuyCourse);


    }

    @ApiOperation(value = "根据课程id查询课程相关信息跨模块")
    @GetMapping("getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable("courseId") String courseId){
        //1 查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        CourseWebVoForOrder courseWebVoForOrder = new CourseWebVoForOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoForOrder);
        return courseWebVoForOrder;
    }


}
