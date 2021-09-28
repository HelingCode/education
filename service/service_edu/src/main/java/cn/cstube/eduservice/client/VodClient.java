package cn.cstube.eduservice.client;

import cn.cstube.commonutils.R;
import cn.cstube.eduservice.entity.EduVideo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @auther heling
 * @date 2021/9/11
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //"删除视频"
    //请求url必须完整
    //远程调用时，参数注解参数名不能省略@PathVariable("videoId")
    @DeleteMapping("/eduvod/video/delVideo/{videoId}")
    public R delVideo(@PathVariable("videoId") String videoId);


    //批量删除视频
    @DeleteMapping("/eduvod/video/delVideoList")
    public R delVideoList(@RequestParam("videoIdList") List<String> videoIdList);

}



