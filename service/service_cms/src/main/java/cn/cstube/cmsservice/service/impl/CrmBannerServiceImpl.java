package cn.cstube.cmsservice.service.impl;

import cn.cstube.cmsservice.entity.CrmBanner;
import cn.cstube.cmsservice.mapper.CrmBannerMapper;
import cn.cstube.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author heling
 * @since 2021-09-12
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {


    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getAllBanner() {
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}
