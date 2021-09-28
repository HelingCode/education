package cn.cstube.eduservice.listener;

import cn.cstube.baseservice.handler.GuliException;
import cn.cstube.eduservice.entity.EduSubject;
import cn.cstube.eduservice.entity.vo.ExcelSubjectData;
import cn.cstube.eduservice.service.EduSubjectService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther heling
 * @date 2021/8/24
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData>{

    public EduSubjectService subjectService;

    public SubjectExcelListener(){};

    //创建有参构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService = subjectService;
    }


    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {

        //1 读取数据验空
        if(excelSubjectData==null){
            throw new GuliException(20001,"导入课程失败");
        }
        //2判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, excelSubjectData.getOneSubjectName());

        //3一级分类不重复则插入数据库
        if(existOneSubject==null){
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();

        //4判断二级名称是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, excelSubjectData.getTwoSubjectName(),pid);

        //5二级分类不重复插入数据库
        if(existTwoSubject==null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        wrapper.eq("title","name");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;

    }

    //判断二级分类是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","pid");
        wrapper.eq("title","name");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
