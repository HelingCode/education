package cn.cstube.orderservice.client;

import cn.cstube.commonutils.vo.CourseWebVoForOrder;
import cn.cstube.commonutils.vo.UcenterMemberForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther heling
 * @date 2021/9/26
 */

@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程id查询课程相关信息跨模块
    @GetMapping("/eduservice/courseapi/getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable("courseId") String courseId);


}
