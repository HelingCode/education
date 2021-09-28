package cn.cstube.orderservice.service;

import cn.cstube.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author heling
 * @since 2021-09-25
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberId);
}
