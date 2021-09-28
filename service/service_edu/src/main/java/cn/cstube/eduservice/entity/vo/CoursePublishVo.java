package cn.cstube.eduservice.entity.vo;

import lombok.Data;

/**
 * @auther heling
 * @date 2021/9/9
 */
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}
