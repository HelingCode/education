package cn.cstube.ucenterservice.service;

import cn.cstube.ucenterservice.entity.UcenterMember;
import cn.cstube.ucenterservice.entity.vo.LoginVo;
import cn.cstube.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-17
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    Integer countRegister(String day);
}
