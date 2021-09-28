package cn.cstube.staservice.client;

import cn.cstube.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther heling
 * @date 2021/9/27
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //统计注册人数远程调用
    @GetMapping("/ucenterservice/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
