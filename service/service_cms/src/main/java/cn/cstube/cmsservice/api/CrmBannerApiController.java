package cn.cstube.cmsservice.api;

import cn.cstube.cmsservice.entity.CrmBanner;
import cn.cstube.cmsservice.service.CrmBannerService;
import cn.cstube.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther heling
 * @date 2021/9/12
 */

@Api(description="前台banner展示")
@RestController
@RequestMapping("/cmsservice/banner")
@CrossOrigin
public class CrmBannerApiController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "查询所有banner信息")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> bannerList = bannerService.getAllBanner();
        return R.ok().data("bannerList",bannerList);
    }


}
