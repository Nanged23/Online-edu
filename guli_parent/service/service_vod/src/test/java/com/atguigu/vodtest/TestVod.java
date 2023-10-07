package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("e6385c6001f671eeb8836732b68e0102");
        return client.getAcsResponse(request);
    }
    public static void main(String[] argv) {
        getPlayAuth();
    }
    //获取播放凭证
    public static void getPlayAuth(){
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t9MruYtiFkyy8nYdDrQ", "eD2j7OnHEJ5x0XfO8i56F23GeoW6mu");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("e6385c6001f671eeb8836732b68e0102");
        try {
            response=client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
        System.out.println("播放凭证："+response.getPlayAuth());
    }
//获取播放地址
    public static void getPlayUrl() {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t9MruYtiFkyy8nYdDrQ", "eD2j7OnHEJ5x0XfO8i56F23GeoW6mu");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
