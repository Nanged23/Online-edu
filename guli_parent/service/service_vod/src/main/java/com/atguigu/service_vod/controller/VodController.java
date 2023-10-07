package com.atguigu.service_vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.service_vod.Utils.ConstantVodUtils;
import com.atguigu.service_vod.Utils.InitVodCilent;
import com.atguigu.service_vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping
    public R uploadAlyiVideo(MultipartFile file) {
        System.out.println("get video\n" + file);
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        System.out.println("上传视频id为" + videoId);
        return R.ok().data("videoId", videoId);
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id, HttpServletRequest request1) throws Exception {
        //创建初始化对象
        String memberId = JwtUtils.getMemberIdByJwtToken(request1);
        if (memberId == null || memberId.equals("")) {
            System.out.println("no member!");
        }
        DefaultAcsClient client =
                InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,
                        ConstantVodUtils.ACCESS_KEY_SECRET);
        //创建获取凭证request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        //向request设置视频id
        request.setEndpoint("vod.cn-shanghai.aliyuncs.com");
        request.setVideoId(id);
        //调用方法得到凭证
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        String playAuth = response.getPlayAuth();
        return R.ok().data("playAuth", playAuth);
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        System.out.println("要删除的视频id为：" + id);
        return vodService.removeOneAlyVideo(id);
    }

    //删除多个阿里云视频的方法
    //参数多个视频id  List videoIdList
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }
}