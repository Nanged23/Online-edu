import service from "..";

export default {
  getPlayAuth(vid) {
    return service({
      url: `/eduvod/video/getPlayAuth/${vid}`,
      method: "get"
    });
  }
};
