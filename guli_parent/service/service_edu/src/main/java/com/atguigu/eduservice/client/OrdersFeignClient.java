package com.atguigu.eduservice.client;

import org.springframework.stereotype.Component;

@Component
public class OrdersFeignClient implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        System.out.println("ℹ 查询订单出错⭐");
        return false;
    }
}