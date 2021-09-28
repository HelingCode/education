package cn.cstube.eduservice.service;

import cn.cstube.eduservice.entity.EduChapter;
import cn.cstube.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author heling
 * @since 2021-08-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);


}
