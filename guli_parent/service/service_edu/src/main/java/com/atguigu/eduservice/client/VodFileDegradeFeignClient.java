package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    //出错之后会执行
    @Override
    public R removeAlyVideo(String id) {
        System.out.println("ℹ 删除单个视频出错");
        return R.error().message("删除单个视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        System.out.println("ℹ 删除多个视频出错");
        return R.error().message("删除多个视频出错");
    }
}
