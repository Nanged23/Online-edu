package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.stereotype.Component;

@Component
public class UcenterFeignClient implements UcenterClient {
    @Override
    public UcenterMemberOrder getUserInfoOrder(String id) {
        System.out.println("Order::远程调用Ucenter出错");
        return new UcenterMemberOrder();
    }
    //出错之后会执行
}
