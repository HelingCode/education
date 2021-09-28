package cn.cstube.orderservice.client;

import cn.cstube.commonutils.vo.UcenterMemberForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther heling
 * @date 2021/9/26
 */

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据memberId获取用户信息跨模块
    @GetMapping("/ucenterservice/member/getUcenterForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterForOrder(@PathVariable("memberId") String memberId);
}
