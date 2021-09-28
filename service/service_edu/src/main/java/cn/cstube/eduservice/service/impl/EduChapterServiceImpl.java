package cn.cstube.eduservice.service.impl;

import cn.cstube.commonutils.R;
import cn.cstube.eduservice.entity.EduChapter;
import cn.cstube.eduservice.entity.EduVideo;
import cn.cstube.eduservice.entity.vo.ChapterVo;
import cn.cstube.eduservice.entity.vo.VideoVo;
import cn.cstube.eduservice.mapper.EduChapterMapper;
import cn.cstube.eduservice.service.EduChapterService;
import cn.cstube.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author heling
 * @since 2021-08-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        //1根据courseId查询章节集合信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);



        //2根据courseId查询小节集合信息
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = eduVideoService.list(videoWrapper);


        //3遍历章节信息进行封装
        List<ChapterVo> chapterVideoList = new ArrayList<>();
        for (EduChapter eduChapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(eduChapter.getId());
            chapterVo.setTitle(eduChapter.getTitle());

            List<VideoVo> videoVoList = new ArrayList<>();

            for (EduVideo eduVideo : videoList) {

                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    videoVo.setId(eduVideo.getId());
                    videoVo.setTitle(eduVideo.getTitle());
                    videoVoList.add(videoVo);
                }
                chapterVo.setChildren(videoVoList);
            }
            chapterVideoList.add(chapterVo);
        }

        return chapterVideoList;


    }
}
