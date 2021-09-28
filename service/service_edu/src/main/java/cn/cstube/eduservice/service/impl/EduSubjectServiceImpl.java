package cn.cstube.eduservice.service.impl;

import cn.cstube.baseservice.handler.GuliException;
import cn.cstube.eduservice.entity.EduSubject;
import cn.cstube.eduservice.entity.vo.ExcelSubjectData;
import cn.cstube.eduservice.entity.vo.OneSubjectVo;
import cn.cstube.eduservice.entity.vo.TwoSubjectVo;
import cn.cstube.eduservice.listener.SubjectExcelListener;
import cn.cstube.eduservice.mapper.EduSubjectMapper;
import cn.cstube.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author heling
 * @since 2021-08-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //批量导入课程分类
    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {

        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"导入课程失败");
        }


    }

    //查询所有课程分类
    @Override
    public List<OneSubjectVo> getAllSubject() {
        //1查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //3封装一级分类
        List<OneSubjectVo> allSubjectList = new ArrayList<>();

        for (EduSubject eduSubject : oneSubjectList) {
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
//            oneSubjectVo.setId(eduSubject.getId());
//            oneSubjectVo.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject,oneSubjectVo);



            //4找到跟一级有关的二级进行封装
            List<TwoSubjectVo> twoSubjectVos = new ArrayList<>();

            for (EduSubject subject : twoSubjectList) {
                //判断是否归属于此一级
                if(subject.getParentId().equals(eduSubject.getId())){
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(subject,twoSubjectVo);
                    twoSubjectVos.add(twoSubjectVo);
                }

            }
            oneSubjectVo.setChildren(twoSubjectVos);
            allSubjectList.add(oneSubjectVo);
        }

        return allSubjectList;
    }
}
