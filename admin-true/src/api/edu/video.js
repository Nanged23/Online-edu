import request from "@/utils/request";
export default {
  //添加小节
  addVideo(video) {
    return request({
      url: "/eduservice/video/addVideo",
      method: "post",
      data: video
    });
  },
  //回滚小节
  getVideo(id) {
    console.log(id);
    return request({
      url: `/eduservice/video/getVideoInfo/${id}`,
      method: "get"
    });
  },
  //修改小节
  updateVideo(video) {
    console.log(video);
    return request({
      url: "/eduservice/video/updateVideo",
      method: "post",
      headers: {
        "Content-Type": "application/json;charset=utf-8" //改这里就好了
      },
      data: video
    });
  },
  //删除小节
  deleteVideo(id) {
    return request({
      url: "/eduservice/video/" + id,
      method: "delete"
    });
  },
  uploadFile(param) {
    console.log("123");
    return request({
      url: "/eduvod/video/uploadAlyiVideo",
      method: "POST",
      headers: {
        "content-type": "multipart/form-data",
        Accept: "application/json"
      },
      data: param
    });
  },
  //删除视频
  deleteAliyunvod(id) {
    return request({
      url: "/eduvod/video/removeAlyVideo/" + id,
      method: "delete"
    });
  }
};
