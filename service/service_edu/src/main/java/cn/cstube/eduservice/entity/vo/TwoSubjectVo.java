package cn.cstube.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @auther heling
 * @date 2021/8/24
 */
@Data
public class TwoSubjectVo {

    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;
}
