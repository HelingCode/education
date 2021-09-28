package cn.cstube.staservice.scheduled;

import cn.cstube.staservice.service.StatisticsDailyService;
import cn.cstube.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @auther heling
 * @date 2021/9/27
 */
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService dailyService;

    //每天凌晨1点跑前一天数据
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task2(){
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        dailyService.createStaDaily(day);
        System.out.println("生成数据成功"+day);
    }
}
