package cn.cstube.smsservice.service;


import java.util.Map;

/**
 * @auther heling
 * @date 2021/9/19
 */
public interface SmsService {
    boolean sendSmsPhone(String phone, Map<String, String> map);
}
