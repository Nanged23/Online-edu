package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.stereotype.Component;

@Component
public class EduFeignClient implements EduClient {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        System.out.println("Order::远程调用edu查询出错");
        return new CourseWebVoOrder();
    }
    //出错之后会执行
}
