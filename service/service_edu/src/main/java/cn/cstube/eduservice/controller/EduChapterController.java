package cn.cstube.eduservice.controller;


import cn.cstube.commonutils.R;
import cn.cstube.eduservice.entity.EduChapter;
import cn.cstube.eduservice.entity.vo.ChapterVo;
import cn.cstube.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author heling
 * @since 2021-08-26
 */
@Api(description="章节管理")
@RestController
@RequestMapping("/eduservice/educhapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "根据课程id查询章节、小节信息")
    @GetMapping("getChapterVideoById/{courseId}")
    public R getChapterVideoById(@PathVariable String courseId){
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoById(courseId);
        return R.ok().data("chapterVideoList",chapterVoList);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @ApiOperation(value = "根据id删除章节")
    @DeleteMapping("delChapter/{id}")
    public R delChapter(@PathVariable String id){
        eduChapterService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询章节")
    @GetMapping("getChapter/{id}")
    public R getChapter(@PathVariable String id){
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.ok().data("eduChapter",eduChapter);
    }

    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

}

