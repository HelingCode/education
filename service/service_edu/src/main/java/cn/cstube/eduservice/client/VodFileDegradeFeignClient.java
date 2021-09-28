package cn.cstube.eduservice.client;

import cn.cstube.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther heling
 * @date 2021/9/12
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R delVideo(String videoId) {
        return R.error().message("删除失败");
    }

    @Override
    public R delVideoList(List<String> videoIdList) {
        return R.error().message("删除失败");
    }
}
